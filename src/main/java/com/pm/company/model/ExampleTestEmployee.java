package com.pm.company.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pmackiewicz on 2016-02-03.
 * JPA 2.0
 */
@Entity
@Table(name = "exampletest_employee")
public class ExampleTestEmployee {

    @Id
    @GeneratedValue
    @Column(name="employee_id")
    private Long id;

    @Column(name="first_name",length = 50, nullable = false)
    private String firstName;

    @Column(name="last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name="salary", scale=10, precision=2, nullable = false)
    private BigDecimal salary;

    @ElementCollection
    @CollectionTable(
            name="exampletest_phone",
            joinColumns=@JoinColumn(name="owner_id")
    )
    private List<ExampleTestPhone> phones;

    protected ExampleTestEmployee() {
    }

    public ExampleTestEmployee(String firstName, String lastName, BigDecimal salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public List<ExampleTestPhone> getPhones() {
        return phones;
    }

    public void setPhones(List<ExampleTestPhone> phones) {
        this.phones = phones;
    }

    public void addPhone(ExampleTestPhone phone){
        if(phones == null || phones.isEmpty()){
            phones = new ArrayList<>();
        }
        phones.add(phone);
    }

    @Override
    public String toString() {
        return "ExampleTestEmployee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                '}';
    }
}
