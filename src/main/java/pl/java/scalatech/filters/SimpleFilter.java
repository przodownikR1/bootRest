package pl.java.scalatech.filters;

import java.io.IOException;
import java.util.Random;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.MDC;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class SimpleFilter implements Filter{

    private  Random random = new Random(); 
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
     log.info("init simpleFilter");
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
           log.info( "req : filter ok !");
           
           if(random.nextBoolean()==true){
               MDC.put("userid","przodownik" );
               log.info("+++ przodownik");               
           }else{
               MDC.put("userid", "tyson");
               log.info("+++ tyson");
           }
           
           try {
               chain.doFilter(request, response);
           } finally {
               MDC.remove("userid");
           }
        
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }

}
