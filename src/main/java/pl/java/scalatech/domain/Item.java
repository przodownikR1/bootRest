package pl.java.scalatech.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class Item extends Audit {
   
    private static final long serialVersionUID = -2051502774891291777L;
    private String name;
    private BigDecimal price;
    

}
