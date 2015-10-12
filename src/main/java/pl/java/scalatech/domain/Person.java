package pl.java.scalatech.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.money.MonetaryAmount;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Data
public class Person implements Serializable{

    private static final long serialVersionUID = 5279859664147821207L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private final String firstname;
    private final String email;
    
    private LocalDate modify;

    @DateTimeFormat(pattern = "dd/MM/yy")
    @NotNull
    @Past
    private LocalDateTime birthDay;
    //TODO
    //private MonetaryAmount basePrice;

}
