package pl.java.scalatech.web.handler;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RateLimitterHandler implements HandlerInterceptor{
    private static final int REQ_LIMIT = 30;
    private static final int TIME_LIMIT = 60;
    private static AccessCounter accessCounter = AccessCounter.getInstance();
    
    
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String ipAddress = getIpAddress(httpServletRequest);
        AccessData accessData = accessCounter.getByIp(ipAddress);
        if (accessData != null) {
            if (!requestLimitExceeded(accessData)) {
                accessData = accessCounter.put(ipAddress);
                httpServletResponse.addIntHeader("X-RateLimit-Limit",REQ_LIMIT);
                httpServletResponse.addHeader("X-RateLimit-Remaining",String.valueOf(REQ_LIMIT - getCurrentCount(accessData)));
            } else {
                httpServletResponse.addIntHeader("Retry-After",TIME_LIMIT);
                httpServletResponse.sendError(429);
            }
        } else {
            accessData = accessCounter.put(ipAddress);
            httpServletResponse.addIntHeader("X-RateLimit-Limit",REQ_LIMIT);
            httpServletResponse.addHeader("X-RateLimit-Remaining",String.valueOf(REQ_LIMIT - getCurrentCount(accessData)));
        }
        
        filterChain.doFilter(servletRequest, servletResponse);

    }
    
    private String getIpAddress(HttpServletRequest httpServletRequest) {        
        String ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = httpServletRequest.getRemoteAddr();
        }
        return ipAddress;
    }
    
    private long getCurrentCount(AccessData accessData) {
        return accessData.getCount().longValue();
    }
    private boolean requestLimitExceeded(AccessData accessData) {
        long currentCount = getCurrentCount(accessData);

        if (currentCount < REQ_LIMIT){
            return false;
        }
        
        return true;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // TODO Auto-generated method stub
        
    }
}
