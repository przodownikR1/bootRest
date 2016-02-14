package pl.java.scalatech.service.user;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class WeatherService {

    @Async
    @SuppressWarnings("unchecked")
    public CompletableFuture<Map<String,Object>> getWeather(String where, String api) {
        log.info("get weather ->  {}  -> api key :   {}", where,api);
        Map<String, Object> result = restTemplate().getForObject("http://api.openweathermap.org/data/2.5/weather?q=" + where+"&APPID="+api, Map.class);
        return CompletableFuture.completedFuture(result);
    }
    
    
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}