package pl.java.scalatech.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.java.scalatech.domain.Skill.SkillBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name="skills")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Feature extends Audit{


    private static final long serialVersionUID = -6945005550536866524L;

}
