package pl.java.scalatech.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "endpoints.git", ignoreUnknownFields = false)
public class GitEndpoint extends AbstractEndpoint<GitProperties> {

 
  @Autowired
  private transient GitProperties gitProperties;

 
  public GitEndpoint() {
    super("git");
  }

  @Override
  public String getId() {
    return "git";
  }

  @Override
  public GitProperties invoke() {
    return gitProperties;
  }
}