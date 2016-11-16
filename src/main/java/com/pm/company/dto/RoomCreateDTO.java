package com.pm.company.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by pmackiewicz on 2015-11-05.
 */
public class RoomCreateDTO {

    @Size(min = 1, max = 5)
    private String number;

    @NotNull
    private Long departmentId;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "RoomCreateDTO{" +
                "number='" + number + '\'' +
                ", departmentId=" + departmentId +
                '}';
    }
}
