package pl.java.scalatech.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=true)
@Table(name="technologies")
public class Technology extends AbstractEntity{

    private static final long serialVersionUID = -5081677479422948664L;
    @Column(name="skillName",nullable=false)
    private String name;
    @Column(name="skillDesc")
    private String desc;
    @Enumerated
    private KnownleageLevel knownleageLevel;
    
}
