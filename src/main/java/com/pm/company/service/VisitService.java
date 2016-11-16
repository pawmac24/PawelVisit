package com.pm.company.service;

import java.util.Date;
import java.util.List;

import com.pm.company.dto.VisitCreateDTO;
import com.pm.company.dto.VisitUpdateDTO;
import com.pm.company.dto.primarykey.VisitPrimaryKeyDTO;
import com.pm.company.dto.result.VisitResultDTO;
import com.pm.company.exception.BusinessValidationException;

public interface VisitService {
	
	VisitPrimaryKeyDTO create(VisitCreateDTO visitDTO) throws BusinessValidationException;

	void update(VisitUpdateDTO visitDTO, Long visitId) throws BusinessValidationException;

	VisitResultDTO find(Long visitId) throws BusinessValidationException;

	void delete(Long visitId) throws BusinessValidationException;

	List<VisitResultDTO> findAll();

	boolean exists(Long visitId);

	boolean periodOverlapsWithExistingEmployeesVisits(Date start, Date end,
			Long employeeId);

	boolean periodOverlapsWithOtherExistingEmployeesVisits(Date start, Date end,
			Long employeeId, Long visitId);

	boolean periodOverlapsWithExistingCustomerVisits(Date start, Date end,
			Long customerId);

	boolean periodOverlapsWithOtherExistingCustomersVisits(Date start, Date end,
			Long customerId, Long visitId);

}
