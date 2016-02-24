package pl.java.scalatech.web.images;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/upload")
@Slf4j
public class ImageUploadController {

    @RequestMapping(value = "/")
    public String fileUpload() {
        return "upload";
    }
    
    @RequestMapping(value = "/sample")
    public String sample() {
        return "sample";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
        log.info("++++  file {} , name : {}",file,name);
        if (!file.isEmpty()) {
            try {
                byte[] bytes =file.getBytes();

                log.info("OriginalFilename : " + file.getOriginalFilename());
                log.info("Size : " + file.getSize());
                
                // Creating the directory to store file
                String rootPath = System.getProperty("user.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream( new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                log.info("Server File Location="
                        + serverFile.getAbsolutePath());
                return "You successfully uploaded file=" + name;
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You failed to upload ");
        stringBuilder.append(name);
        stringBuilder.append(" because the file was empty.");
        return stringBuilder.toString();
    
     

    }
    
    
    @RequestMapping(value = "/upload2", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, method = RequestMethod.POST)
    public ResponseEntity<Void> uploadFile(@RequestPart String description, @RequestPart MultipartFile file) {
        //yaay!
        return ResponseEntity.ok(null);
    }
}
