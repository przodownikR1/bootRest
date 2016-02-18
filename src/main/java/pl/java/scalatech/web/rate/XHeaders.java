package pl.java.scalatech.web.rate;
public interface XHeaders {
  
    String RATE_LIMIT = "X-RateLimit-Limit";
    String RATE_RESET = "X-RateLimit-Reset";
    String RATE_REMAINING = "X-RateLimit-Remaining";
    String TOTAL_COUNT = "X-Total-Count";
  
  
}