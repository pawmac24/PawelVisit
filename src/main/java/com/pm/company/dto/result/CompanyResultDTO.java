package com.pm.company.dto.result;

import com.pm.company.dto.AddressDTO;

/**
 * Created by pmackiewicz on 2015-11-04.
 */
public class CompanyResultDTO {
    private Long companyId;

    private String name;

    private AddressDTO address;

    public CompanyResultDTO() {
    }

    public CompanyResultDTO(Long companyId, String name, AddressDTO address) {
        this.companyId = companyId;
        this.name = name;
        this.address = address;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
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

    @Override
    public String toString() {
        return "CompanyResultDTO{" +
                "companyId=" + companyId +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}
