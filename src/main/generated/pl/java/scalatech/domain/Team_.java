package pl.java.scalatech.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Team.class)
public abstract class Team_ extends pl.java.scalatech.domain.AbstractEntity_ {

	public static volatile SingularAttribute<Team, String> name;
	public static volatile ListAttribute<Team, User> users;
	public static volatile SingularAttribute<Team, String> desc;

}

