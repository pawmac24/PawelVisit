package com.pm.company.dto;

import com.pm.company.model.Person;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by pmackiewicz on 2015-10-21.
 */
public class EmployeeCreateDTO implements Person {

    @NotBlank
    @Size(min = 1, max = 20)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 40)
    private String lastName;

    @Size(max = 80)
    private String description;

    @NotNull
    private Long departmentId;

    public EmployeeCreateDTO() {
		super();
	}

	public EmployeeCreateDTO(String firstName, String lastName, String description, Long departmentId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
        this.departmentId = departmentId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "EmployeeCreateDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                ", departmentId=" + departmentId +
                '}';
    }
}
