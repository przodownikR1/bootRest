package pl.java.scalatech.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="projects")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Builder
public class Project extends AbstractEntity{
    
    private static final long serialVersionUID = 8490634203238895620L;
    @Column(name="projectName",nullable=false)
    private String name;
    @Column(name="projectDesc")
    private String desc;
    
    @OneToMany
    List<Person> people;
    
}
