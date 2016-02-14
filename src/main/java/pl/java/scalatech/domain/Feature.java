package pl.java.scalatech.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name="skills")
@Builder
public class Feature extends Audit{


    private static final long serialVersionUID = -6945005550536866524L;

}
