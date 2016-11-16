package com.pm.company.dto.result;

import com.pm.company.dto.AddressDTO;

/**
 * Created by pmackiewicz on 2015-11-04.
 */
public class DepartmentResultDTO {
    private Long departmentId;

    private String name;

    private AddressDTO address;

    private Long companyId;

    private String companyName;

    public DepartmentResultDTO() {
    }

    public DepartmentResultDTO(Long departmentId, String name, AddressDTO address, Long companyId) {
        this.departmentId = departmentId;
        this.name = name;
        this.address = address;
        this.companyId = companyId;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    @Override
    public String toString() {
        return "DepartmentResultDTO{" +
                "departmentId=" + departmentId +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", companyId=" + companyId +
                ", companyName=" + companyName +
                '}';
    }
}
