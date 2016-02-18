package pl.java.scalatech.web.date;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import lombok.Data;

@RestController
@RequestMapping("/api/logs")
public class LogsController {

    @RequestMapping
    public List<LoggerDTO> getList() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        return context.getLoggerList().stream().map(LoggerDTO::new).collect(Collectors.toList());
    }

    @Data
    static class LoggerDTO {

        private String name;
        private String level;

        public LoggerDTO(Logger logger) {
            this.name = logger.getName();
            this.level = logger.getEffectiveLevel().toString();
        }

        @JsonCreator
        public LoggerDTO() {
        }
    }

}