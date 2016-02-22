package pl.java.scalatech.domain.poll;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Simple.class)
public abstract class Simple_ {

	public static volatile SingularAttribute<Simple, LocalDateTime> lastModifiedDate;
	public static volatile SingularAttribute<Simple, String> name;
	public static volatile SingularAttribute<Simple, Long> id;
	public static volatile SingularAttribute<Simple, Long> version;
	public static volatile SingularAttribute<Simple, LocalDateTime> createDate;

}

