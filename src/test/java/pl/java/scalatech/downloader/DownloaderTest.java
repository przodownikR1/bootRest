package pl.java.scalatech.downloader;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.tools.HttpContentDownloader;

@Slf4j
public class DownloaderTest {
    @Test
    @SneakyThrows
    public void shouldDownloaderWork() {
        String content = HttpContentDownloader.fetchAsString("http://www.webservicex.net/globalweather.asmx?wsdl");
        log.info("+++  {}", content);
        assertThat(content).isNotNull();
    }
    
  

}
