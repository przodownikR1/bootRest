package pl.java.scalatech.web.pictures;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.AbstractResource;
import org.springframework.util.Assert;

public class PhotoResource extends AbstractResource {

	private final Photo photo;
	private String desc;

    public PhotoResource(Photo photo) {
        Assert.notNull(photo, "Photo must not be null");
        this.photo = photo;
    }

    @Override
    public String getDescription() {
        return this.desc;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.photo.getInputStream();
    }
}