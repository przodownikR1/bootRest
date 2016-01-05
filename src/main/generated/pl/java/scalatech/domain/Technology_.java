package pl.java.scalatech.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Technology.class)
public abstract class Technology_ extends pl.java.scalatech.domain.AbstractEntity_ {

	public static volatile SingularAttribute<Technology, KnownleageLevel> knownleageLevel;
	public static volatile SingularAttribute<Technology, String> name;
	public static volatile SingularAttribute<Technology, String> desc;

}

