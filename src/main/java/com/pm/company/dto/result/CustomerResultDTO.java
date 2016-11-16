package com.pm.company.dto.result;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by pmackiewicz on 2015-10-21.
 */
public class CustomerResultDTO{

    private Long customerId;

    private String pesel;

    private String firstName;

    private String lastName;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date dateOfBirth;

    public CustomerResultDTO() {
		super();
	}

	public CustomerResultDTO(Long customerId, String pesel, String firstName, String lastName, Date dateOfBirth) {
		super();
		this.customerId = customerId;
		this.pesel = pesel;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
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

    @Override
    public String toString() {
        return "CustomerResultDTO{" +
                "customerId=" + customerId +
                ", pesel='" + pesel + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
