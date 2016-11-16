package com.pm.company.validator.business;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pm.company.model.Department;
import com.pm.company.model.Employee;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pm.company.exception.BusinessValidationException;
import com.pm.company.service.EmployeeService;

public class EmployeeCreateBusinessValidatorTest {

	@InjectMocks
	private EmployeeBusinessValidator sut;
	
	@Mock
	private EmployeeService employeeSrv;
	
	private Employee employee;
	
	private Long employeeId;
	
	@Before
	public void before(){
		employee = new Employee("Firstname", "Lastname", "description", new Department());
		employeeId = 1L;
		sut = new EmployeeBusinessValidator();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void ensureThatExistShouldPass() throws BusinessValidationException{
		when(employeeSrv.exists(employeeId)).thenReturn(true);
		sut.ensureThatExists(employeeId);
		verify(employeeSrv, times(1)).exists(employeeId);
	}
	
	@Test(expected = BusinessValidationException.class)
	public void ensureThatExistShouldFail() throws BusinessValidationException{
		when(employeeSrv.exists(employeeId)).thenReturn(false);
		sut.ensureThatExists(employeeId);
	}
	
}
