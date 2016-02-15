package pl.java.scalatech.bean;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MySpringContextRefreshedEventListener {

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        log.info("handleContextRefresh  {}",event);
    }

}