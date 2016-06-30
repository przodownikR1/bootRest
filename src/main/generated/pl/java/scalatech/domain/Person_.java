package pl.java.scalatech.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Person.class)
public abstract class Person_ extends pl.java.scalatech.domain.AbstractEntity_ {

	public static volatile SingularAttribute<Person, LocalDate> modify;
	public static volatile SingularAttribute<Person, LocalDateTime> birthDay;
	public static volatile SingularAttribute<Person, String> firstname;
	public static volatile SingularAttribute<Person, String> email;

}

