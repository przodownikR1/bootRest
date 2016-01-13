package pl.java.scalatech.config;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import net.bull.javamelody.MonitoredWithAnnotationPointcut;
import net.bull.javamelody.MonitoringSpringAdvisor;
import net.bull.javamelody.SpringDataSourceBeanPostProcessor;

@Configuration
@Profile("javamelody")
public class JavaMelodyConfig {

    @Bean
    public MonitoringSpringAdvisor annotationMonitoringAdvisor() {
        MonitoringSpringAdvisor annotationMonitoringAdvisor = new MonitoringSpringAdvisor();
        annotationMonitoringAdvisor.setPointcut(new MonitoredWithAnnotationPointcut()); // Add pointcut that matches javamelody's @MonitoredWithSpring annotated
                                                                                        // methods
        return annotationMonitoringAdvisor;
    }

    @Bean
    public static MonitoringSpringAdvisor requestMonitoringAdvisor() {
        MonitoringSpringAdvisor requestMonitoringAdvisor = new MonitoringSpringAdvisor();
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* pl.java.scalatech..*.*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)");
        requestMonitoringAdvisor.setPointcut(pointcut);
        return requestMonitoringAdvisor;
    }

    @Bean
    public static MonitoringSpringAdvisor serviceMonitoringAdvisor() {
        MonitoringSpringAdvisor serviceMonitoringAdvisor = new MonitoringSpringAdvisor();
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public * pl.java.scalatech..*.*(..)) and within(@org.springframework.stereotype.Service *)");
        serviceMonitoringAdvisor.setPointcut(pointcut);
        return serviceMonitoringAdvisor;
    }

    @Bean
    public static SpringDataSourceBeanPostProcessor springDataSourceBeanPostProcessor() {
        SpringDataSourceBeanPostProcessor springDataSourceBeanPostProcessor = new SpringDataSourceBeanPostProcessor();
        return springDataSourceBeanPostProcessor;
    }

}