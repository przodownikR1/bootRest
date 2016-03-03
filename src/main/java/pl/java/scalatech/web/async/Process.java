package pl.java.scalatech.web.async;

import java.util.concurrent.Future;

public interface Process {
    Future<Void> async(String data);
    String sync(String data);
}
