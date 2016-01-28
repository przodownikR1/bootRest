package pl.java.scalatech.web.pictures;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

import java.io.IOException;
import java.net.URI;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;

@RestController
public class PicturesController {

    @Resource
    private org.springframework.core.io.Resource picture;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "person/{personId}", produces = IMAGE_JPEG_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public org.springframework.core.io.Resource getUserPhoto(@PathVariable String personId) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new PhotoResource(() -> picture.getInputStream());
    }

    @RequestMapping(method = RequestMethod.POST, value = "person/{userId}")
    public ResponseEntity<?> postPhoto(@PathVariable Long userId, @RequestParam MultipartFile file, UriComponentsBuilder uriBuilder) throws IOException {
        Photo photo = file::getInputStream;
        User user = userRepository.findOne(userId);

        URI uri = uriBuilder.path("/person/{userId}").buildAndExpand(userId, user.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
