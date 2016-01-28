package pl.java.scalatech.web.pictures;

import java.io.IOException;
import java.io.InputStream;

public interface Photo {
	public InputStream getInputStream() throws IOException;

}