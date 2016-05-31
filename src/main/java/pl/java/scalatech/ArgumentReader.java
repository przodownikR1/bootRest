package pl.java.scalatech;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
class ArgumentReader {
    @Autowired
    public ArgumentReader(ApplicationArguments args) {
        boolean enable = args.containsOption("enable");
        log.info("enabled : {}",enable);
        List<String> _args = args.getNonOptionArgs();
        log.info("## > extra args ... {}",_args);
    }
}