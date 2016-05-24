package pl.java.scalatech.retry;

import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrdinaryBean {

    @Retryable(include=MyShittyException.class,maxAttempts=3)
    public void printEx(){
       log.info("++++ retry");
       throw new MyShittyException();
    }

    public String printTestTemplate(){
        log.info("++++ retry template");
        throw new MyShittyException();

    }

    @Retryable(maxAttempts=2)
    public void print(){
       log.info("++++ retry");
       throw new IllegalStateException();
    }

    @Recover
    public void rescue(IllegalArgumentException ex){
      log.info("++++ rescue  {}",ex);


    }

}
