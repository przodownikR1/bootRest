package pl.java.scalatech.web.test;

import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.KnownleageLevel;
import pl.java.scalatech.domain.Skill;
@RestController
@Slf4j
public class RestlogController {
private static final String REQUEST_ID = "requestID";
    
    private static final ObjectMapper jsonMapper = new ObjectMapper();
    
    @RequestMapping("/logTest")
    public String logging() {
        MDC.put(REQUEST_ID, UUID.randomUUID().toString());
        log.trace("this is a trace message");
        log.debug("this is a debug message");
        log.info("this is a normal info message");
        log.warn("this is a warning msg");
        log.error("this is a error message");
        MDC.clear();
        return "trace/info/debug/warn/error logs";
    }
    
    @RequestMapping("/logSkill")
    public String logPerson() throws JsonProcessingException  {
        
        MDC.put(REQUEST_ID, UUID.randomUUID().toString());
        Skill skill = Skill.builder().name("java").knownleageLevel(KnownleageLevel.HIGH).build();
        log.debug(jsonMapper.writeValueAsString(skill));
        MDC.clear();
        return "person logged";
    }
}
