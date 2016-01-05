package pl.java.scalatech.config;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.jmx.JmxMetricWriter;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Profile;
import org.springframework.jmx.export.MBeanExporter;

import com.google.common.collect.ImmutableMap;

@Configuration
@EnableMBeanExport
public class JmxConfig {

   
    @Bean
    @DependsOn("statisticsService")
    @Profile("dev")
    public MBeanExporter jmxService(Statistics statistics) {
        MBeanExporter exporter = new MBeanExporter();
        exporter.setBeans(ImmutableMap.of("Hibernate:application=Statistics", (Object) statistics));
        return exporter;
    }
    
    @Bean
    @ExportMetricWriter
    @Profile("dev")
    MetricWriter jmxMetric(Statistics statistics){
        return new JmxMetricWriter(jmxService(statistics));
    }
    
    @Bean
    @ExportMetricWriter
    @Profile("prod")
    MetricWriter jmxMetric(MBeanExporter exporter){
        return new JmxMetricWriter(exporter);
    }
    

    @Bean
    @DependsOn("entityManagerFactory")
    @Profile("dev")
    public Statistics statisticsService(EntityManagerFactory entityManagerFactory) {
        SessionFactory sf = ((HibernateEntityManagerFactory) entityManagerFactory).getSessionFactory();
        return sf.getStatistics();
    }

}
