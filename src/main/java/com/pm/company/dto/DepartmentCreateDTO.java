package com.pm.company.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by pmackiewicz on 2015-11-04.
 */
public class DepartmentCreateDTO {

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    private AddressDTO address;

    @NotNull
    private Long companyId;

    public DepartmentCreateDTO() {
    }

    public DepartmentCreateDTO(String name, AddressDTO address, Long companyId) {
        this.name = name;
        this.address = address;
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "DepartmentCreateDTO{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", companyId=" + companyId +
                '}';
    }
}
