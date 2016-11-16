package com.pm.company.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by pmackiewicz on 2015-11-04.
 */
@Embeddable
public class Address {

    @Column(name = "city", length = 40, nullable = false)
    private String city;

    @Column(name = "postcode", length = 10, nullable = false)
    private String postcode;

    @Column(name = "street", length = 50, nullable = false)
    private String street;

    @Column(name = "number", length = 10, nullable = false)
    private String number;

    public Address() {
    }

    public Address(String city, String postcode, String street, String number) {
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
        return "Address{" +
                "city='" + city + '\'' +
                ", postcode='" + postcode + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
