package pl.java.scalatech.web.async;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.java.scalatech.service.user.WeatherService;

@RestController
@PropertySource("classpath:weather.properties")
public class WeatherServiceController {
    @Autowired
    private WeatherService weatherService;
  

    @Autowired
    private Environment env;

    @RequestMapping("/weather")
    CompletableFuture<?> hello(@RequestParam Optional<String> where) {
        return weatherService.getWeather(where.orElse("Warsaw"), env.getProperty("api"));
    }
}
