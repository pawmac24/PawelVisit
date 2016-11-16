package com.pm.company.validator.business;

import com.pm.company.repository.CustomerRepository;
import com.pm.company.repository.EmployeeRepository;
import com.pm.company.model.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pm.company.exception.BusinessValidationException;
import com.pm.company.service.VisitService;
import com.pm.company.validator.helper.VisitValidationHelper;

@Component
public class VisitBusinessValidator implements BusinessValidator<Visit, Long>{

	@Autowired
	private VisitService visitService;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private VisitValidationHelper validationHelper;

	public void ensureThatExists(Long visitId) throws BusinessValidationException {
		if (!visitService.exists(visitId)) {
			throw new BusinessValidationException("Visit with id: " + visitId + " does not exist");
		}
	}

	@Override
	public void validateCreate(Visit visit) throws BusinessValidationException {
		if (!customerRepo.exists(visit.getCustomer().getCustomerId())) {
			throw new BusinessValidationException(
					"Customer with id: " + visit.getCustomer().getCustomerId() + " does not exist");
		}
		if (!employeeRepo.exists(visit.getEmployee().getEmployeeId())) {
			throw new BusinessValidationException(
					"Employee with id: " + visit.getEmployee().getEmployeeId() + " does not exist");
		}
		if (visitService.periodOverlapsWithExistingCustomerVisits(visit.getStart(),
				visit.getEnd(), visit.getCustomer().getCustomerId())) {
			throw new BusinessValidationException("Visit overlaps with other visits of this customer");
		}
		if (visitService.periodOverlapsWithExistingEmployeesVisits(visit.getStart(),
				visit.getEnd(), visit.getEmployee().getEmployeeId())) {
			throw new BusinessValidationException("Visit overlaps with other visits of this employee");
		}
		validationHelper.validateAgainstSchedule(visit.getStart(), visit.getEnd(), visit.getEmployee().getEmployeeId());
	}

	@Override
	public void validateUpdate(Visit visit) throws BusinessValidationException {
		if (visitService.periodOverlapsWithOtherExistingCustomersVisits(visit.getStart(),
				visit.getEnd(), visit.getCustomer().getCustomerId(), visit.getVisitId())) {
			throw new BusinessValidationException("Visit overlaps with other visits of this customer");
		}
		if (visitService.periodOverlapsWithOtherExistingEmployeesVisits(visit.getStart(),
				visit.getEnd(), visit.getEmployee().getEmployeeId(), visit.getVisitId())) {
			throw new BusinessValidationException("Visit overlaps with other visits of this employee");
		}
		validationHelper.validateAgainstSchedule(visit.getStart(), visit.getEnd(), visit.getEmployee().getEmployeeId());
	}

	@Override
	public void validateDelete(Long visitId) throws BusinessValidationException {
	}
}
