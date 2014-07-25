package bo.gov.aduana.bandeja.entities;

import bo.gov.aduana.bandeja.entities.User.Rol;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, Rol> rol;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, Boolean> activo;

}

