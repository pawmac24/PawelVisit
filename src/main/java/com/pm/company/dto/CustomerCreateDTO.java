package com.pm.company.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.pm.company.model.Person;
import org.hibernate.validator.constraints.NotBlank;

public class CustomerCreateDTO implements Person {

    @NotBlank
    @Size(min = 11, max =11, message = "Pesel size must equals exactly 11 digits")
    private String pesel;

    @NotBlank
    @Size(min = 1, max = 20)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 40)
    private String lastName;

    @NotNull
    @Past
    private Date dateOfBirth;

	public CustomerCreateDTO() {
	}

	public CustomerCreateDTO(String pesel, String firstName, String lastName, Date dateOfBirth) {
		this.pesel = pesel;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
    
    
}
