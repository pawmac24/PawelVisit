package com.pm.company.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by pmackiewicz on 2015-11-04.
 */

@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue
    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name="company_id", nullable = false)
    @NotNull
    private Company company;

    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<Room> rooms;

    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    public Department() {
    }

    public Department(String name, Address address, Company company) {
        this.name = name;
        this.address = address;
        this.company = company;
    }


    public Long getCompanyId() {
        return this.company.getCompanyId();
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + departmentId +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}
