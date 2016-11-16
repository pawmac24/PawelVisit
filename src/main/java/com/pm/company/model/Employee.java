package com.pm.company.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by pmackiewicz on 2015-10-20.
 */

@Entity
@Table(name ="employee")
public class Employee implements Person{
    @Id
    @GeneratedValue
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "first_name", length = 20, nullable = false)
    @NotNull
    @Size(min = 1, max = 20)
    private String firstName;

    @Column(name = "last_name", length = 40, nullable = false)
    @NotNull
    @Size(min = 1, max = 40)
    private String lastName;

    @Column(length = 80)
    @Size(max = 80)
    private String description;
    
    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    private List<Visit> visits;
    
    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    private List<Schedule> schedules;

    @ManyToOne
    @JoinColumn(name="department_id", nullable = false)
    @NotNull
    private Department department;

    public Employee() {
		super();
	}

	public Employee(String firstName, String lastName, String description, Department department) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
        this.department = department;
	}

	public Employee(Long employeeId, String firstName, String lastName, String description, Department department) {
		this(firstName, lastName, description, department);
		this.employeeId = employeeId;
	}

	public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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

    public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
