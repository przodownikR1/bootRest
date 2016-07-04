package pl.java.scalatech.config;

import java.lang.management.ManagementFactory;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
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

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.metrics.DatabaseH2HealthCheck;
import pl.java.scalatech.metrics.DiskCapacityHealthCheck;
import pl.java.scalatech.metrics.MemoryHealthCheck;
import pl.java.scalatech.metrics.Ping;

@Configuration
@Slf4j
@Profile("metrics")
@EnableMetrics
public class MetricsConfig extends MetricsConfigurerAdapter{

    private static final String JVM_MEMORY = "jvm.memory";
    private static final String JVM_GARBAGE = "jvm.garbage";
    private static final String JVM_THREADS = "jvm.threads";
    private static final String JVM_FILES = "jvm.files";
    private static final String JVM_BUFFERS = "jvm.buffers";
    
    @Autowired
    private DataSource dataSource;    
   
    
    @Bean
    HealthCheckRegistry healthCheckRegistry(){
        return new HealthCheckRegistry();
    }

    @Bean
    Histogram searchResultHistogram(MetricRegistry metricRegistry) {
        return metricRegistry.histogram("histogram.search.results");
    }

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        ConsoleReporter
                .forRegistry(metricRegistry)
                .build()
                .start(10, TimeUnit.MINUTES);
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
    BufferPoolMetricSet poolMetrics(MetricRegistry metricRegistry){
        return metricRegistry.register(JVM_BUFFERS, new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()));
    }

   
    
    @Bean
    @SneakyThrows(SQLException.class)
    public HealthCheck healthCheck() {
        final HealthCheck healthCheck = new MemoryHealthCheck();
        final DatabaseH2HealthCheck h2Check = new DatabaseH2HealthCheck(dataSource);
        final Ping ping= new Ping();
        final DiskCapacityHealthCheck disk = new DiskCapacityHealthCheck();        
        healthCheckRegistry().register("mem", healthCheck);
        healthCheckRegistry().register("ping", ping);
        healthCheckRegistry().register("disk", disk);
        healthCheckRegistry().register("h2", h2Check);
        return healthCheck;        
    }
    
   
    
    @Bean
    @Profile("jmx-metrics")
    public JmxReporter jmxReporter(MetricRegistry metricRegistry) {
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
        HealthCheckServlet hc = new HealthCheckServlet(healthCheckRegistry());
        ServletRegistrationBean srb = new ServletRegistrationBean(hc, "/health/*");
        srb.setLoadOnStartup(2);
        return srb;
    }
}
