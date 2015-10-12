package pl.java.scalatech.domain;

import static javax.persistence.GenerationType.AUTO;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Item extends Audit {
    @Id
    @GeneratedValue(strategy=AUTO)
    private Long id;
    private String name;
    private BigDecimal price;
    

}
