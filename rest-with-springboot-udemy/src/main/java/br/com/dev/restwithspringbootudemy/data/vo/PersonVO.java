package br.com.dev.restwithspringbootudemy.data.vo;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dozermapper.core.Mapping;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PersonVO extends ResourceSupport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Mapping("id")
	@JsonProperty("id")
	private Long key;
	private String firstName;
	private String lastName;
	private String address;
	private String gender;
	private boolean enabled;
}
