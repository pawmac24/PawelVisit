package com.pm.company.validator.business;

import com.pm.company.exception.BusinessValidationException;
import com.pm.company.model.Department;
import com.pm.company.repository.EmployeeRepository;
import com.pm.company.repository.RoomRepository;
import com.pm.company.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepartmentBusinessValidator implements BusinessValidator<Department, Long> {

	@Autowired
	private DepartmentService departmentSrv;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void validateCreate(Department validatedObject) throws BusinessValidationException {
	}

	@Override
	public void validateUpdate(Department validatedObject) throws BusinessValidationException {
	}

	@Override
	public void validateDelete(Long departmentId) throws BusinessValidationException {
		ensureThatExists(departmentId);
		Long roomCount = roomRepository.countRooms(departmentId);
		if(roomCount > 0 ) {
			throw new BusinessValidationException("It is not possible to delete department with id " + departmentId +
					" which have rooms. Please delete all rooms which belongs to that department first");
		}
		Long employeeCount = employeeRepository.countEmployees(departmentId);
		if(employeeCount > 0 ) {
			throw new BusinessValidationException("It is not possible to delete department with id " + departmentId +
					" which have employees. Please delete all employees which belongs to that department first");
		}
	}

	@Override
	public void ensureThatExists(Long validatedObjectId) throws BusinessValidationException {
		if(!departmentSrv.exists(validatedObjectId)){
			throw new BusinessValidationException("Department with id: " + validatedObjectId + " does not exist");
		}
	}
}
