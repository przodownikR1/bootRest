package pl.java.scalatech.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends pl.java.scalatech.domain.AbstractEntity_ {

	public static volatile SingularAttribute<User, String> firstname;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> login;
	public static volatile SingularAttribute<User, String> email;

}

