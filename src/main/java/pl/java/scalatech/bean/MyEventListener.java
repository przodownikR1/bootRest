package pl.java.scalatech.bean;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyEventListener {

    @EventListener
    @Order(value = 1)
    public void handleMyEvent(Event event) {
        log.info("handleMyEvent1");
    }

    @EventListener
    @Order(value = 0)
    public Boolean handleCustomEvent(Event event) {
        log.info("handleCustomEvent0");
        return Boolean.TRUE;
    }

}