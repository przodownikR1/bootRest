package pl.java.scalatech.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Project.class)
public abstract class Project_ extends pl.java.scalatech.domain.AbstractEntity_ {

	public static volatile SingularAttribute<Project, String> name;
	public static volatile ListAttribute<Project, Person> people;
	public static volatile SingularAttribute<Project, String> desc;

}

