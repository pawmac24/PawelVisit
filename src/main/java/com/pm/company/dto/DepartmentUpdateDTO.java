package com.pm.company.dto;

/**
 * Created by pmackiewicz on 2015-11-04.
 */
public class DepartmentUpdateDTO {
    private Long departmentId;

    private String name;

    private AddressDTO address;

    public DepartmentUpdateDTO() {
    }

    public DepartmentUpdateDTO(Long departmentId, String name, AddressDTO address) {
        this.departmentId = departmentId;
        this.name = name;
        this.address = address;
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

    @Override
    public String toString() {
        return "DepartmentUpdateDTO{" +
                "departmentId=" + departmentId +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}
