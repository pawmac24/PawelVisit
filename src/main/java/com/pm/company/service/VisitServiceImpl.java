package com.pm.company.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pm.company.dto.primarykey.VisitPrimaryKeyDTO;
import com.pm.company.dto.result.VisitResultDTO;
import com.pm.company.dto.VisitCreateDTO;
import com.pm.company.dto.VisitUpdateDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.model.Customer;
import com.pm.company.model.Employee;
import com.pm.company.model.Visit;
import com.pm.company.repository.CustomerRepository;
import com.pm.company.repository.VisitRepository;
import com.pm.company.validator.business.BusinessValidator;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.company.repository.EmployeeRepository;

@Service
public class VisitServiceImpl implements VisitService {

	public final static Logger logger = Logger.getLogger(VisitServiceImpl.class);

	@Autowired
	private VisitRepository visitRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private BusinessValidator<Visit, Long> visitValidator;

	@Autowired
	private Mapper mapper;

	@Override
	public VisitPrimaryKeyDTO create(VisitCreateDTO visitDTO) throws BusinessValidationException {
		Employee employee = employeeRepo.findOne(visitDTO.getEmployeeId());
		Customer customer = customerRepo.findOne(visitDTO.getCustomerId());
		Visit visit = mapper.map(visitDTO, Visit.class);
		visit.setEmployee(employee);
		visit.setCustomer(customer);
		visitValidator.validateCreate(visit);

		visit = visitRepo.save(visit);
		logger.info("create " + visit);
		VisitPrimaryKeyDTO visitPrimaryKeyDTO = new VisitPrimaryKeyDTO(visit.getVisitId());
		return visitPrimaryKeyDTO;
	}

	@Override
	public void update(VisitUpdateDTO visitUpdateDTO, Long visitId) throws BusinessValidationException {
		Visit visit = getVisitById(visitId);
		mapper.map(visitUpdateDTO, visit);
		visitValidator.validateUpdate(visit);

		visit = visitRepo.save(visit);
		logger.info("update " + visit);
	}


	@Override
	public VisitResultDTO find(Long visitId) throws BusinessValidationException {
		Visit visit = getVisitById(visitId);
		VisitResultDTO visitResultDTO = mapper.map(visit, VisitResultDTO.class);
		return visitResultDTO;
	}

	@Override
	public void delete(Long visitId) throws BusinessValidationException {
		visitValidator.ensureThatExists(visitId);
		visitRepo.delete(visitId);
	}

	@Override
	public List<VisitResultDTO> findAll() {
		List<Visit> visitList = visitRepo.findAll();
		List<VisitResultDTO> visitResultDTOList = new ArrayList<>();

		for (Visit visit : visitList) {
			visitResultDTOList.add(mapper.map(visit, VisitResultDTO.class));
		}
		return visitResultDTOList;
	}

	@Override
	public boolean exists(Long visitId) {
		return visitRepo.exists(visitId);
	}

	@Override
	public boolean periodOverlapsWithExistingEmployeesVisits(Date start, Date end, Long employeeId) {
		return visitRepo.countEmployeesOverlappingVisits(start, end, employeeId) > 0L;
	}

	@Override
	public boolean periodOverlapsWithOtherExistingEmployeesVisits(Date start, Date end, Long employeeId, Long visitId) {
		return visitRepo.countEmployeesOverlappingVisitsOmitting(start, end, employeeId, visitId) > 0L;
	}

	@Override
	public boolean periodOverlapsWithExistingCustomerVisits(Date start, Date end, Long customerId) {
		return visitRepo.countCustomerOverlappingVisits(start, end, customerId) > 0L;
	}

	@Override
	public boolean periodOverlapsWithOtherExistingCustomersVisits(Date start, Date end, Long customerId, Long visitId) {
		return visitRepo.countCustomerOverlappingVisitsOmitting(start, end, customerId, visitId) > 0L;
	}

	private Visit getVisitById(Long visitId) throws BusinessValidationException {
		visitValidator.ensureThatExists(visitId);
		return visitRepo.findOne(visitId);
	}
}
