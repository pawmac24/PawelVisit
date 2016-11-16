package com.pm.company.service;

import com.pm.company.dto.EmployeeCreateDTO;
import com.pm.company.dto.EmployeeUpdateDTO;
import com.pm.company.dto.primarykey.EmployeePrimaryKeyDTO;
import com.pm.company.dto.result.EmployeeResultDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.model.Department;
import com.pm.company.model.Employee;
import com.pm.company.repository.DepartmentRepository;
import com.pm.company.repository.EmployeeRepository;
import com.pm.company.validator.business.BusinessValidator;

import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pmackiewicz on 2015-10-20.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	public final static Logger logger = Logger.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private BusinessValidator<Employee, Long> employeeValidator;

	@Autowired
	private Mapper mapper;

	@Override
	public List<EmployeeResultDTO> findAll() {
		List<Employee> employeeList = employeeRepository.findAll();
		List<EmployeeResultDTO> employeeResultDTOList = new ArrayList<>();

		for (Employee employee : employeeList) {
			employeeResultDTOList.add(mapper.map(employee, EmployeeResultDTO.class, "caseListEmployees"));
		}
		return employeeResultDTOList;
	}

	@Override
	public List<EmployeeResultDTO> findAllByDepartmentId(Long departmentId) {
		List<Employee> employeeList = employeeRepository.findByDepartmentId(departmentId);
		List<EmployeeResultDTO> employeeResultDTOList = new ArrayList<>();

		for (Employee employee : employeeList) {
			employeeResultDTOList.add(mapper.map(employee, EmployeeResultDTO.class, "caseListEmployees"));
		}
		return employeeResultDTOList;
	}

	@Override
	public EmployeeResultDTO find(Long employeeId) throws BusinessValidationException {
		Employee employee = getEmployeeById(employeeId);
		EmployeeResultDTO employeeResultDTO = mapper.map(employee, EmployeeResultDTO.class);
		return employeeResultDTO;
	}


	@Override
	public EmployeePrimaryKeyDTO create(EmployeeCreateDTO employeeDTO) throws BusinessValidationException{
		Department department = departmentRepository.findOne(employeeDTO.getDepartmentId());
		Employee employee = mapper.map(employeeDTO, Employee.class);
		employee.setDepartment(department);
		employeeValidator.validateCreate(employee);

		employee = employeeRepository.save(employee);
		logger.info("create " + employee);
		EmployeePrimaryKeyDTO employeePKeyDTO = new EmployeePrimaryKeyDTO(employee.getEmployeeId());
		return employeePKeyDTO;
	}

	@Override
	public void update(Long employeeId, EmployeeUpdateDTO employeeDTO) throws BusinessValidationException {
		Employee employee = getEmployeeById(employeeId);
		mapper.map(employeeDTO, employee);
		employeeValidator.validateUpdate(employee);

		employee = employeeRepository.save(employee);
		logger.info("update " + employee);
	}

	@Override
	public void delete(Long employeeId) throws BusinessValidationException {
		employeeValidator.validateDelete(employeeId);
		employeeRepository.delete(employeeId);
	}

	@Override
	public boolean exists(Long employeeId) {
		return employeeRepository.exists(employeeId);
	}

	private Employee getEmployeeById(Long employeeId) throws BusinessValidationException {
		Employee employee = employeeRepository.findOne(employeeId);
		if(employee == null){
			throw new BusinessValidationException("Employee with id: " + employeeId + " does not exist");
		}
		return employee;
	}
}
