package com.pm.company.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pm.company.model.Person;
import org.hibernate.validator.constraints.NotBlank;

public class CustomerUpdateDTO implements Person {

	@NotBlank
	@Size(min = 1, max = 20)
	private String firstName;

	@NotNull
	@Size(min = 1, max = 40)
	private String lastName;

	public CustomerUpdateDTO() {
	}
	
	public CustomerUpdateDTO(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
