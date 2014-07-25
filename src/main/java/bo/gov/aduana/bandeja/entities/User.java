package bo.gov.aduana.bandeja.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Representa los usuarios autorizados a ingresar al sistema
 * @author sintroini
 * 
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
	
	private static final long serialVersionUID = -920289181399947956L;
	
	public enum Rol {
        ADMIN,USER 
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String username;
	
	@NotNull
	private String password;
	
	@Enumerated(EnumType.STRING)
    private Rol rol;
	
    private Boolean activo;
    
    public Rol getRol() { return rol; }
    
    public void setRol(Rol rol) {
        this.rol = rol;        
    }
    
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
}
