package com.pm.company.dto;

import com.pm.company.model.Person;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by pmackiewicz on 2015-10-21.
 */
public class EmployeeUpdateDTO implements Person {

    @NotBlank
    @Size(min = 1, max = 20)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 40)
    private String lastName;

    private String description;

    public EmployeeUpdateDTO() {
    }

    public EmployeeUpdateDTO(String firstName, String lastName, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getFirstName() {
        return firstName;
    }

	public String getLastName() {
        return lastName;
    }

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    public void setLastName(String lastName) {
		this.lastName = lastName;
	}

    @Override
    public String toString() {
        return "EmployeeUpdateDTO{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
