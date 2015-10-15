package pl.java.scalatech.web.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Payload {
    @Getter @Setter 
    private String message;
}
