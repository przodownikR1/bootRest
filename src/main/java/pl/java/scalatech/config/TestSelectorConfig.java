package pl.java.scalatech.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages= {"pl.java.scalatech.service","pl.java.scalatech.repository"})
@EnableJpaRepositories(basePackages="pl.java.scalatech.repository")
@EntityScan(basePackages="pl.java.scalatech.domain")
//@EnableAutoConfiguration
@Import({DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TestSelectorConfig {

}
