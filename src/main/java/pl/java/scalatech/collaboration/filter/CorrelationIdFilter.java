package pl.java.scalatech.collaboration.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.base.Strings;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.collaboration.CorrelationId;


@Slf4j
public class CorrelationIdFilter implements Filter {

    @Value("${spring.application.name}")
    private String appName;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String correlationId = retrieveCorrelationId(servletRequest);
        MDC.put(CorrelationId.CORRELATION_ID_HEADER_KEY, correlationId);
        chain.doFilter(new CorrelationIdServletRequestWrapper(servletRequest, correlationId), response);
    }

    private String retrieveCorrelationId(HttpServletRequest servletRequest) {
        String correlationId = servletRequest.getHeader(CorrelationId.CORRELATION_ID_HEADER_KEY);
        if (Strings.isNullOrEmpty(correlationId)) {
            correlationId = appName + "_" + UUID.randomUUID().toString();
            log.info("Providing new X-Correlation-Id : {}", correlationId);
        } else {
            log.info("Reusing existing X-Correlation-Id : {}", correlationId);
        }
        return correlationId;
    }

    @Override
    public void destroy() {

    }
}