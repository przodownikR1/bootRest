package pl.java.scalatech.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RateLimiter implements Filter {
	
  //TODO switch on redis instance
	
	private static final int REQ_LIMIT = Integer.MAX_VALUE;
	private static final int TIME_LIMIT = 600000; 
	
	private static AccessCounter accessCounter = AccessCounter.getInstance();

	@Override
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
	private boolean requestLimitExceeded(AccessData accessData) {
		long currentCount = getCurrentCount(accessData);

		if (currentCount < REQ_LIMIT){
			return false;
		}
		
		return true;
	}
	private long getCurrentCount(AccessData accessData) {
		return accessData.getCount().longValue();
	}

	private String getIpAddress(HttpServletRequest httpServletRequest) {
		//is client behind something?
		String ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");  
		if (ipAddress == null) {  
			ipAddress = httpServletRequest.getRemoteAddr();  
		}
		
		return ipAddress;
	}


	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}