package com.pm.company.service;

import com.pm.company.dto.CompanyCreateDTO;
import com.pm.company.dto.CompanyUpdateDTO;
import com.pm.company.dto.primarykey.CompanyPrimaryKeyDTO;
import com.pm.company.dto.result.CompanyResultDTO;
import com.pm.company.exception.BusinessValidationException;

import java.util.List;

/**
 * Created by pmackiewicz on 2015-11-04.
 */
public interface CompanyService {

    CompanyPrimaryKeyDTO create(CompanyCreateDTO companyDTO) throws BusinessValidationException;

    void update(Long companyId, CompanyUpdateDTO companyDTO) throws BusinessValidationException;

    CompanyResultDTO find(Long companyId) throws BusinessValidationException;

    void delete(Long customerId) throws BusinessValidationException;

    List<CompanyResultDTO> findAll();

    boolean exists(Long companyId);

}
