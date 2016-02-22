package pl.java.scalatech.domain.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import pl.java.scalatech.config.AsyncConfig;
import pl.java.scalatech.domain.repo.MyRepositoryFactoryBean;

@Configuration
//@EnableJpaRepositories(basePackages="pl.java.scalatech.repository")
@EntityScan(basePackages="pl.java.scalatech.domain"/*,basePackageClasses={Jsr310JpaConverters.class}*/)
@Import({DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,AsyncConfig.class})
@ComponentScan(basePackages="pl.java.scalatech.domain")
@Profile("test")
@EnableJpaRepositories(repositoryFactoryBeanClass = MyRepositoryFactoryBean.class)
public class TupleConfig {

}
