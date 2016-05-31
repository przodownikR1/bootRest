package pl.java.scalatech;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.ToString;

@Component
@ConfigurationProperties(prefix="rest")
@Data
@ToString
public class MyRestProperties {
private String name;
private int rateLimitter;
private int cacheTll;

}