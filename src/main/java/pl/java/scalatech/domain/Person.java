package pl.java.scalatech.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class Person extends AbstractEntity{

    private static final long serialVersionUID = 5279859664147821207L;


    private  String firstname;
    private  String email;
    
    private LocalDate modify;

    @DateTimeFormat(pattern = "dd/MM/yy")
    @NotNull
    @Past
    private LocalDateTime birthDay;
    //TODO
    //private MonetaryAmount basePrice;

}
