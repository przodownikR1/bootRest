package pl.java.scalatech.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name="skills")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Skill extends AbstractEntity{

    @Column(name="skillName",nullable=false)
    private String name;
    @Column(name="skillDesc")
    private String desc;
    @Enumerated
    private KnownleageLevel knownleageLevel;
}
