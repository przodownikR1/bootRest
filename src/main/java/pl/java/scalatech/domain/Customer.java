package pl.java.scalatech.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
@Entity
@Data
@Slf4j
@EqualsAndHashCode(callSuper=true)
public class Customer extends AbstractEntity {
    
    private String name;
    
    @JsonCreator
    public Customer(@JsonProperty("id") Long id){
        this.id = id;
    }
    
    @JsonCreator
    public Customer(){}

}
