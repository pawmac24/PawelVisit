package com.pm.company.service;

import com.pm.company.dto.DepartmentCreateDTO;
import com.pm.company.dto.result.DepartmentResultDTO;
import com.pm.company.dto.DepartmentUpdateDTO;
import com.pm.company.dto.primarykey.DepartmentPrimaryKeyDTO;
import com.pm.company.exception.BusinessValidationException;

import java.util.List;

/**
 * Created by pmackiewicz on 2015-11-04.
 */
public interface DepartmentService {

    DepartmentPrimaryKeyDTO create(DepartmentCreateDTO companyDTO) throws BusinessValidationException;

    void update(Long companyId, DepartmentUpdateDTO companyDTO) throws BusinessValidationException;

    DepartmentResultDTO find(Long departmentId) throws BusinessValidationException;

    void delete(Long customerId) throws BusinessValidationException;

    List<DepartmentResultDTO> findAll();

    List<DepartmentResultDTO> findAllByCompanyId(Long companyId);

    boolean exists(Long departmentId);
}
