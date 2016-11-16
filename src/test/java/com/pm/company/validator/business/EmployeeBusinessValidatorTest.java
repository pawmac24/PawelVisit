package com.pm.company.validator.business;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pm.company.model.Department;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pm.company.model.Employee;

public class EmployeeBusinessValidatorTest {

	@InjectMocks
	private EmployeeBusinessValidator sut;
	
	@Mock
	private EmployeeService employeeSrv;
	
	private Employee employee;
	
	@Before
	public void before(){
		employee = new Employee(1L, "Firstname", "Lastname", "description", new Department());
		sut = new EmployeeBusinessValidator();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void ensureThatExistShouldPass() throws BusinessValidationException {
		when(employeeSrv.exists(employee.getEmployeeId())).thenReturn(true);
		sut.ensureThatExists(employee.getEmployeeId());
		verify(employeeSrv, times(1)).exists(employee.getEmployeeId());
	}
	
	@Test(expected = BusinessValidationException.class)
	public void ensureThatExistShouldFail() throws BusinessValidationException{
		when(employeeSrv.exists(employee.getEmployeeId())).thenReturn(false);
		sut.ensureThatExists(employee.getEmployeeId());
	}
	
}
