package pl.java.scalatech.domain;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Item.class)
public abstract class Item_ extends pl.java.scalatech.domain.Audit_ {

	public static volatile SingularAttribute<Item, BigDecimal> price;
	public static volatile SingularAttribute<Item, String> name;

}

