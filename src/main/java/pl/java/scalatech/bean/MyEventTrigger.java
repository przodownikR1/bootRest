package pl.java.scalatech.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MyEventTrigger {

    private final ApplicationEventPublisher publisher;

    @Autowired
    public MyEventTrigger(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishEvent(Event event) {
        this.publisher.publishEvent(event);
    }

}