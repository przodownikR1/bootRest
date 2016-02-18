package pl.java.scalatech.web.date;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/date")
public class DateController {

    @RequestMapping
    public ResponseEntity<LocalDateTime> getCurrentDate() {
        Date createdAt = new Date();
        LocalDateTime time = LocalDateTime.ofInstant(createdAt.toInstant(), ZoneId.systemDefault());
        return ResponseEntity.ok(time);
    }
}
