package com.pm.company.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by pmackiewicz on 2015-10-20.
 */
@Entity
@Table(name = "customer")
public class Customer implements Person{

    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Long customerId;

    @Column(length = 11, nullable = false, unique = true)
    @NotNull
    @Size(min = 11, max =11, message = "Pesel size must equals exactly 11 digits")
    private String pesel;

    @Column(name = "first_name", length = 20, nullable = false)
    @NotNull
    @Size(min = 1, max = 20)
    private String firstName;

    @Column(name = "last_name", length = 40, nullable = false)
    @NotNull
    @Size(min = 1, max = 40)
    private String lastName;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth", nullable = false)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @NotNull
    @Past
    private Date dateOfBirth;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Visit> visits;
    
    public Customer(){
    	
    }
    
    public Customer(String pesel, String firstName, String lastName, Date dateOfBirth) {
		this.pesel = pesel;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

    public Customer(Long customerId, String pesel, String firstName, String lastName, Date dateOfBirth) {
        this(pesel, firstName, lastName, dateOfBirth);
        this.customerId = customerId;
    }

	public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

	@Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", pesel='" + pesel + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

}
