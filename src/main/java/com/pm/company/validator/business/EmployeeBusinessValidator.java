package com.pm.company.validator.business;

import com.pm.company.repository.ScheduleRepository;
import com.pm.company.repository.UserRepository;
import com.pm.company.repository.VisitRepository;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.service.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pm.company.model.Employee;

@Component
public class EmployeeBusinessValidator implements BusinessValidator<Employee, Long> {

	public final static Logger logger = Logger.getLogger(EmployeeBusinessValidator.class);

	@Autowired
	private EmployeeService employeeSrv;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private VisitRepository visitRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void validateCreate(Employee validatedObject) throws BusinessValidationException {
	}

	@Override
	public void validateUpdate(Employee validatedObject) throws BusinessValidationException {
	}

	@Override
	public void validateDelete(Long employeeId) throws BusinessValidationException {
		ensureThatExists(employeeId);
		Long schedulesCount = scheduleRepository.countSchedulesByEmployeeId(employeeId);
		logger.info("validateDelete schedulesCount = " + schedulesCount);
		if(schedulesCount > 0 ) {
			throw new BusinessValidationException("It is not possible to delete employee with id " + employeeId +
					" which have schedules. Please delete all schedules which belongs to that employee first");
		}
		Long visitsCount = visitRepository.countVisitsByEmployeeId(employeeId);
		logger.info("validateDelete visitsCount = " + visitsCount);
		if(visitsCount > 0 ) {
			throw new BusinessValidationException("It is not possible to delete employee with id " + employeeId +
					" which have visits. Please delete all visits which belongs to that employee first");
		}
		Long usersCount = userRepository.countUsers(employeeId);
		logger.info("validateDelete usersCount = " + usersCount);
		if(usersCount > 0 ) {
			throw new BusinessValidationException("It is not possible to delete employee with id " + employeeId +
					" which have user. Please delete user which is related to this employee");
		}
	}

	@Override
	public void ensureThatExists(Long validatedObjectId) throws BusinessValidationException {
		if (!employeeSrv.exists(validatedObjectId)) {
			throw new BusinessValidationException("Employee with id: " + validatedObjectId + " does not exist");
		}
	}
}
