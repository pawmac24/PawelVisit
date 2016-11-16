package com.pm.company.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by pmackiewicz on 2015-11-04.
 */
public class AddressDTO {

    @NotBlank
    @Size(min = 1, max = 40)
    private String city;

    @NotBlank
    @Size(min = 1, max = 10)
    private String postcode;

    @NotBlank
    @Size(min = 1, max = 50)
    private String street;

    @NotBlank
    @Size(min = 1, max = 10)
    private String number;

    public AddressDTO() {
    }

    public AddressDTO(String city, String postcode, String street, String number) {
        this.city = city;
        this.postcode = postcode;
        this.street = street;
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "city='" + city + '\'' +
                ", postcode='" + postcode + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
