/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.java.scalatech.config;

import static com.google.common.collect.ImmutableMap.of;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.hibernate.stat.Statistics;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.jmx.JmxMetricWriter;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Profile;
import org.springframework.jmx.export.MBeanExporter;

@Configuration
@EnableMBeanExport
class JmxConfig {

   
    @Bean
    @DependsOn("statisticsService")
    @Profile("metrics")
    public MBeanExporter jmxService(Statistics statistics) {
        MBeanExporter exporter = new MBeanExporter();
        exporter.setBeans(of("Hibernate:application=Statistics", (Object) statistics));
        return exporter;
    }
    
    @Bean
    @ExportMetricWriter
    @Profile("metrics")
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
    @Profile("metrics")
    public Statistics statisticsService(EntityManagerFactory entityManagerFactory) {
        SessionFactory sf = ((HibernateEntityManagerFactory) entityManagerFactory).getSessionFactory();
        return sf.getStatistics();
    }

}
