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

    private static final long serialVersionUID = -7954597941468761979L;
    @Column(name="teamName",nullable=false)
    private String name;
    @Column(name="teamDesc")
    private String desc;
    
    @ManyToMany
    List<User> users;
    
}
