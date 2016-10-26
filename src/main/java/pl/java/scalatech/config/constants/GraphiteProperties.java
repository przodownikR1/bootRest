package pl.java.scalatech.config.constants;

import javax.validation.constraints.NotNull;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.codahale.metrics.graphite.Graphite;

import lombok.Data;

@Component
@ConditionalOnClass(Graphite.class)
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "graphite")
@Data
public class GraphiteProperties {
    @NotNull
    private int port;
    @NotNull
    private String host;
    @NotNull
    private String name;
}
