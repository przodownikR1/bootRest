package pl.java.scalatech.config.admin;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import de.codecentric.boot.admin.config.EnableAdminServer;

@Configuration
//@EnableAdminServer
//@Profile("admin")
public class AdminBootConfig extends WebMvcConfigurerAdapter{
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(31556926);
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/META-INF/spring-boot-admin-server-ui/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/images/**").addResourceLocations("/images/").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/META-INF/spring-boot-admin-server-ui/js/").setCachePeriod(31556926);
        registry.addResourceHandler("index.html").addResourceLocations("classpath:/META-INF/spring-boot-admin-server-ui/");
    }
    
}