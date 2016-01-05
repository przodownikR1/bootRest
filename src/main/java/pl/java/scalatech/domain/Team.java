package pl.java.scalatech.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=true)
@Table(name="teams")
public class Team extends AbstractEntity{

    @Column(name="teamName",nullable=false)
    private String name;
    @Column(name="teamDesc")
    private String desc;
    
    @ManyToMany
    List<User> users;
    
}
