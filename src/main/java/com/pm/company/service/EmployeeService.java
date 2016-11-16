package com.pm.company.service;

import java.util.List;

import com.pm.company.dto.EmployeeCreateDTO;
import com.pm.company.dto.EmployeeUpdateDTO;
import com.pm.company.dto.primarykey.EmployeePrimaryKeyDTO;
import com.pm.company.dto.result.EmployeeResultDTO;
import com.pm.company.exception.BusinessValidationException;

/**
 * Created by pmackiewicz on 2015-10-20.
 */
public interface EmployeeService {

    List<EmployeeResultDTO> findAll();

    List<EmployeeResultDTO> findAllByDepartmentId(Long departmentId);

    EmployeeResultDTO find(Long employeeId) throws BusinessValidationException;

    public EmployeePrimaryKeyDTO create(EmployeeCreateDTO employeeDTO) throws BusinessValidationException;

    void delete(Long employeeId) throws BusinessValidationException;

    boolean exists(Long employeeId);

    void update(Long employeeId, EmployeeUpdateDTO employeeDTO) throws BusinessValidationException;
}
