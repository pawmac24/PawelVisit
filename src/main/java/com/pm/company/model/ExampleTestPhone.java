package com.pm.company.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by pmackiewicz on 2016-02-03.
 * JPA 2.0
 */
@Embeddable
public class ExampleTestPhone {

    @Column(name="type", length = 5, nullable = false)
    private String type;

    @Column(name="area_code", length = 3, nullable = false)
    private String areaCode;

    @Column(name="phone_number", length = 8, nullable = false)
    private String number;

    protected ExampleTestPhone() {
    }

    public ExampleTestPhone(String type, String areaCode, String number) {
        this.type = type;
        this.areaCode = areaCode;
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ExampleTestPhone{" +
                "type='" + type + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
