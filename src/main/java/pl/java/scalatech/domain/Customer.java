package pl.java.scalatech.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
@Entity
@Data
@Slf4j
public class Customer {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    
    @JsonCreator
    public Customer(@JsonProperty("id") Long id){
        this.id = id;
    }
    
    @JsonCreator
    public Customer(){}

}
