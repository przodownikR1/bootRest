package pl.java.scalatech.domain.poll;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Poll.class)
public abstract class Poll_ extends pl.java.scalatech.domain.Audit_ {

	public static volatile SingularAttribute<Poll, String> question;
	public static volatile SetAttribute<Poll, Option> options;

}

