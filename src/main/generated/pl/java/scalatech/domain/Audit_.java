package pl.java.scalatech.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Audit.class)
public abstract class Audit_ extends pl.java.scalatech.domain.AbstractEntity_ {

	public static volatile SingularAttribute<Audit, LocalDate> createdDate;
	public static volatile SingularAttribute<Audit, LocalDate> lastModifiedDate;
	public static volatile SingularAttribute<Audit, Person> createdBy;
	public static volatile SingularAttribute<Audit, Person> lastModifiedBy;

}

