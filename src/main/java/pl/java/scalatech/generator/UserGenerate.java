package pl.java.scalatech.generator;

import java.math.BigDecimal;
import java.util.Random;

import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

import com.codahale.metrics.annotation.Timed;
import com.github.javafaker.Faker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserGenerate {

    private Random r = new Random();
    private Faker faker;
    Position[] positions;
    Currency[] currencies;
    public UserGenerate() {
        faker = new Faker();
        positions = Position.values();
        currencies = Currency.values();
    }

    // @formatter:off
    @Handler
    @Timed(name="userGenerator")
    public User generateSingleUser() {//Currency[] currencies 
        User user = User.builder()
                .email(faker.internet().emailAddress())
                .creditCard(faker.business().creditCardNumber())
                .creditCardType(faker.business().creditCardType())
                .city(faker.address().city())
                .country(faker.address().country())                  ////
                .currency(currencies[r.nextInt(currencies.length-1)])///
                .login(faker.name().fullName())
                .name(faker.name().lastName())
                .position(positions[r.nextInt(positions.length-1)])
                .salary(new BigDecimal(r.nextInt(10000)))
                .build();
                log.debug("user: {}",user);
        return user;
    }
 // @formatter:on
   
  
}
