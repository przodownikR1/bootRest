package pl.java.scalatech.tools;

import static org.apache.http.client.fluent.Request.Get;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;

public final class HttpContentDownloader {

    private HttpContentDownloader() {
    
    }
    
    public static String fetchAsString(String url) throws ClientProtocolException, IOException {
        return Get(url).execute().returnContent().asString();
    }
}