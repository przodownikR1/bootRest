package pl.java.scalatech.config.constants;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static pl.java.scalatech.config.constants.MetricsConstant.JVM_BUFFERS;
import static pl.java.scalatech.config.constants.MetricsConstant.JVM_FILES;
import static pl.java.scalatech.config.constants.MetricsConstant.JVM_GARBAGE;
import static pl.java.scalatech.config.constants.MetricsConstant.JVM_MEMORY;
import static pl.java.scalatech.config.constants.MetricsConstant.JVM_THREADS;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.jvm.BufferPoolMetricSet;
import com.codahale.metrics.jvm.FileDescriptorRatioGauge;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

import pl.java.scalatech.metrics.DatabaseH2HealthCheck;
import pl.java.scalatech.metrics.DiskCapacityHealthCheck;
import pl.java.scalatech.metrics.MemoryHealthCheck;
import pl.java.scalatech.metrics.PingHealthCheck;
import pl.java.scalatech.metrics.RestResourcesHealthCheck;

@Configuration
@Profile("metrics")
@EnableMetrics(proxyTargetClass = true)
public class MetricsConfig extends MetricsConfigurerAdapter {

    private final DataSource dataSource;

    @Value("${pingUrl}")
    private String pingUrl;

    public MetricsConfig(DataSource dataSource) {       
        this.dataSource = dataSource;
    }

    @Configuration
    @ConditionalOnClass(Graphite.class)
    public static class GraphiteConfig {

        private final MetricRegistry metricRegistry;

        private final GraphiteProperties graphiteProps;

        public GraphiteConfig(MetricRegistry metricRegistry, GraphiteProperties graphiteProps) {
            this.metricRegistry = metricRegistry;
            this.graphiteProps = graphiteProps;
        }

        @PostConstruct
        public void startGraphiteReporter() throws UnknownHostException {
            String hostname = InetAddress.getLocalHost().getHostName();
            metricRegistry.register(JVM_FILES, new FileDescriptorRatioGauge());
            metricRegistry.register(JVM_MEMORY, new MemoryUsageGaugeSet());
            metricRegistry.register(JVM_THREADS, new ThreadStatesGaugeSet());
            metricRegistry.register(JVM_GARBAGE, new GarbageCollectorMetricSet());

            Graphite graphite = new Graphite(new InetSocketAddress(graphiteProps.getHost(), graphiteProps.getPort()));
            GraphiteReporter reporter = GraphiteReporter.forRegistry(metricRegistry).prefixedWith(graphiteProps.getName() + "." + hostname).build(graphite);
            reporter.start(10, TimeUnit.SECONDS);
        }
    }

    private final HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();

    @Bean
    @Override
    public HealthCheckRegistry getHealthCheckRegistry() {
        return healthCheckRegistry;
    }

    @PostConstruct
    public void registerHealthChecks() throws SQLException {
        final HealthCheck healthCheck = new MemoryHealthCheck();
        final DatabaseH2HealthCheck h2Check = new DatabaseH2HealthCheck(dataSource);
        final PingHealthCheck ping = new PingHealthCheck();
        final DiskCapacityHealthCheck disk = new DiskCapacityHealthCheck();
        RestResourcesHealthCheck restResourceHealth = new RestResourcesHealthCheck(pingUrl);
        getHealthCheckRegistry().register("mem", healthCheck);
        getHealthCheckRegistry().register("ping", ping);
        getHealthCheckRegistry().register("disk", disk);
        getHealthCheckRegistry().register("h2", h2Check);
        getHealthCheckRegistry().register("resource", restResourceHealth);
    }

    @Bean
    Histogram searchResultHistogram(MetricRegistry metricRegistry) {
        return metricRegistry.histogram("histogram.search.results");
    }

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        consoleReport(metricRegistry);
        slf4jReport(metricRegistry);
        jmxReport(metricRegistry);
    }

    private void jmxReport(MetricRegistry metricRegistry) {
        JmxReporter.forRegistry(metricRegistry).build().start();
    }

    private void consoleReport(MetricRegistry metricRegistry) {
        ConsoleReporter.forRegistry(metricRegistry).build().start(5, MINUTES);
    }

    private void slf4jReport(MetricRegistry metricRegistry) {
        Slf4jReporter.forRegistry(metricRegistry).outputTo(LoggerFactory.getLogger(getClass().getCanonicalName())).convertRatesTo(SECONDS)
                .convertDurationsTo(MILLISECONDS).build().start(5, MINUTES);
    }

    @Bean
    @Profile("memory-metrics")
    public MemoryUsageGaugeSet memory(MetricRegistry metricRegistry) {
        return metricRegistry.register(JVM_MEMORY, new MemoryUsageGaugeSet());
    }

    @Bean
    @Profile("thread-metrics")
    ThreadStatesGaugeSet thread(MetricRegistry metricRegistry) {
        return metricRegistry.register(JVM_THREADS, new ThreadStatesGaugeSet());
    }

    @Bean
    @Profile("file-metrics")
    FileDescriptorRatioGauge fileMetrics(MetricRegistry metricRegistry) {
        return metricRegistry.register(JVM_FILES, new FileDescriptorRatioGauge());
    }

    @Bean
    @Profile("gc-metrics")
    GarbageCollectorMetricSet gcMetrics(MetricRegistry metricRegistry) {
        return metricRegistry.register(JVM_GARBAGE, new GarbageCollectorMetricSet());
    }

    @Bean
    @Profile("buffer-metrics")
    BufferPoolMetricSet poolMetrics(MetricRegistry metricRegistry) {
        return metricRegistry.register(JVM_BUFFERS, new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()));
    }

    @Profile("jmx-metrics")
    @Bean(destroyMethod = "stop")
    JmxReporter jmxReporter(MetricRegistry metricRegistry) {
        final JmxReporter reporter = JmxReporter.forRegistry(metricRegistry).build();
        reporter.start();
        return reporter;
    }

    @Bean
    @Autowired
    public ServletRegistrationBean servletRegistrationBean(MetricRegistry metricRegistry) {
        MetricsServlet ms = new MetricsServlet(metricRegistry);
        ServletRegistrationBean srb = new ServletRegistrationBean(ms, "/metrics/*");
        srb.setLoadOnStartup(1);
        return srb;

    }

    @Bean
    @Autowired
    public ServletRegistrationBean servletHealthRegistryBean() {
        HealthCheckServlet hc = new HealthCheckServlet(getHealthCheckRegistry());
        ServletRegistrationBean srb = new ServletRegistrationBean(hc, "/health/*");
        srb.setLoadOnStartup(2);
        return srb;
    }
}

/*
 * @PostConstruct
 * public void registerJvmMetrics() {
 * registerAll("gc", new GarbageCollectorMetricSet(), metricRegistry);
 * registerAll("memory", new MemoryUsageGaugeSet(), metricRegistry);
 * }
 * private void registerAll(String prefix, MetricSet metricSet, MetricRegistry registry) {
 * log.info("+++++ metricsSet : {} , registry {}  , prefix : {}  ", metricSet,registry,prefix);
 * for (Map.Entry<String, Metric> entry : metricSet.getMetrics().entrySet()) {
 * if (entry.getValue() instanceof MetricSet) {
 * registerAll(prefix + "." + entry.getKey(), (MetricSet) entry.getValue(), registry);
 * } else {
 * registry.register(prefix + "." + entry.getKey(), entry.getValue());
 * }
 * }
 * }
 */