package pl.java.scalatech.event;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.java.scalatech.bean.Event;
import pl.java.scalatech.bean.MyEventListener;
import pl.java.scalatech.bean.MyEventTrigger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EventTest.Conf.class)
public class EventTest {
    @Configuration
    @ComponentScan(basePackages="pl.java.scalatech.bean")
    static public class Conf {
 
    }
    
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Mock
    ApplicationEventPublisher publisher;
    
    @Mock
    MyEventListener listener;
        
    @Autowired
    private MyEventTrigger trigger;

    @Test
    public void shouldEventWork(){  
        Mockito.doNothing().when(publisher).publishEvent(Mockito.any());
        trigger.publishEvent(new Event("mytype",trigger));
        Mockito.verify(publisher).publishEvent(Mockito.any());
    }
    
    
}
