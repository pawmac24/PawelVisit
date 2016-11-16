package com.pm.company.validator.business;

import com.pm.company.exception.BusinessValidationException;
import com.pm.company.model.Company;
import com.pm.company.repository.DepartmentRepository;
import com.pm.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyBusinessValidator implements BusinessValidator<Company, Long> {

	@Autowired
	private CompanyService companySrv;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public void validateCreate(Company validatedObject) throws BusinessValidationException {

	}

	@Override
	public void validateUpdate(Company validatedObject) throws BusinessValidationException {

	}

	@Override
	public void validateDelete(Long companyId) throws BusinessValidationException {
		ensureThatExists(companyId);
		Long departmentCount = departmentRepository.countDepartments(companyId);
		if(departmentCount > 0 ) {
			throw new BusinessValidationException("It is not possible to delete company with id " + companyId +
					" which have departments. Please delete all departments which belongs to that company first");
		}
	}

	@Override
	public void ensureThatExists(Long validatedObjectId) throws BusinessValidationException {
		if(!companySrv.exists(validatedObjectId)){
			throw new BusinessValidationException("Company with id: " + validatedObjectId + " does not exist");
		}
	}
}
