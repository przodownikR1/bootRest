package pl.java.scalatech.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Skill.class)
public abstract class Skill_ extends pl.java.scalatech.domain.AbstractEntity_ {

	public static volatile SingularAttribute<Skill, KnownleageLevel> knownleageLevel;
	public static volatile SingularAttribute<Skill, String> name;
	public static volatile SingularAttribute<Skill, String> desc;

}

