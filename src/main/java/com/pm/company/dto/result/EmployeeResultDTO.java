package com.pm.company.dto.result;

/**
 * Created by pmackiewicz on 2015-10-21.
 */
public class EmployeeResultDTO {

    private Long employeeId;

    private String firstName;

    private String lastName;

    private String description;

    private String companyName;

    private String departmentName;
    
    public EmployeeResultDTO() {
		super();
	}

	public EmployeeResultDTO(Long employeeId, String firstName, String lastName, String description) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
	}

	public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDescription() {
        return description;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "EmployeeResultDTO{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                ", companyName='" + companyName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
