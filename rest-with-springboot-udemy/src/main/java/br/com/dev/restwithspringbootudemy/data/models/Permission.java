package br.com.dev.restwithspringbootudemy.data.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Entity
@Table(name = "permission")
@Data
public class Permission implements GrantedAuthority, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.description;
	}

}
