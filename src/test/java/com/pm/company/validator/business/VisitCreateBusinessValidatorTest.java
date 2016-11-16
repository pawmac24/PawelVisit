package com.pm.company.validator.business;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import com.pm.company.model.Customer;
import com.pm.company.model.Employee;
import com.pm.company.model.Status;
import com.pm.company.model.Visit;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.repository.CustomerRepository;
import com.pm.company.service.VisitService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.pm.company.repository.EmployeeRepository;
import com.pm.company.validator.helper.VisitValidationHelper;

public class VisitCreateBusinessValidatorTest {

	@InjectMocks
	private VisitBusinessValidator sut;

	@Mock
	private VisitService visitService;
	
	@Mock
	private CustomerRepository customerRepo;

	@Mock
	private EmployeeRepository employeeRepo;

	@Mock
	private VisitValidationHelper validationHelper;

	private Visit visit;
	
	private Long visitId;

	@Before
	public void before() {
		visit = new Visit(new Date(), new Date(), true, new Customer(), new Employee(), Status.NEW.getValue());
		visitId = 1L;
		sut = new VisitBusinessValidator();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldPassValidation() throws BusinessValidationException {
		when(customerRepo.exists(visit.getCustomer().getCustomerId())).thenReturn(true);
		when(employeeRepo.exists(visit.getEmployee().getEmployeeId())).thenReturn(true);
		when(visitService.periodOverlapsWithExistingCustomerVisits(visit.getStart(), visit.getEnd(),
				visit.getCustomer().getCustomerId())).thenReturn(false);
		when(visitService.periodOverlapsWithExistingEmployeesVisits(visit.getStart(), visit.getEnd(),
				visit.getEmployee().getEmployeeId())).thenReturn(false);
		sut.validateCreate(visit);
		verify(customerRepo, times(1)).exists(visit.getCustomer().getCustomerId());
		verify(employeeRepo, times(1)).exists(visit.getEmployee().getEmployeeId());
		verify(visitService, times(1)).periodOverlapsWithExistingCustomerVisits(visit.getStart(), visit.getEnd(),
				visit.getCustomer().getCustomerId());
		verify(visitService, times(1)).periodOverlapsWithExistingEmployeesVisits(visit.getStart(), visit.getEnd(),
				visit.getEmployee().getEmployeeId());
	}

	@Test(expected = BusinessValidationException.class)
	public void shouldFailValidationBecouseOfCustomerOverlaps() throws BusinessValidationException {
		when(customerRepo.exists(visit.getCustomer().getCustomerId())).thenReturn(true);
		when(employeeRepo.exists(visit.getEmployee().getEmployeeId())).thenReturn(true);
		when(visitService.periodOverlapsWithExistingCustomerVisits(visit.getStart(), visit.getEnd(),
				visit.getCustomer().getCustomerId())).thenReturn(true);
		when(visitService.periodOverlapsWithExistingEmployeesVisits(visit.getStart(), visit.getEnd(),
				visit.getEmployee().getEmployeeId())).thenReturn(false);
		sut.validateCreate(visit);
	}

	@Test(expected = BusinessValidationException.class)
	public void shouldFailValidationBecouseOfEmployeeOverlaps() throws BusinessValidationException {
		when(customerRepo.exists(visit.getCustomer().getCustomerId())).thenReturn(true);
		when(employeeRepo.exists(visit.getEmployee().getEmployeeId())).thenReturn(true);
		when(visitService.periodOverlapsWithExistingCustomerVisits(visit.getStart(), visit.getEnd(),
				visit.getCustomer().getCustomerId())).thenReturn(false);
		when(visitService.periodOverlapsWithExistingEmployeesVisits(visit.getStart(), visit.getEnd(),
				visit.getEmployee().getEmployeeId())).thenReturn(true);
		sut.validateCreate(visit);
	}
	
	@Test(expected = BusinessValidationException.class)
	public void shouldFailValidationBecouseOfNonexistientEmployee() throws BusinessValidationException {
		when(customerRepo.exists(visit.getCustomer().getCustomerId())).thenReturn(true);
		when(employeeRepo.exists(visit.getEmployee().getEmployeeId())).thenReturn(false);
		when(visitService.periodOverlapsWithExistingCustomerVisits(visit.getStart(), visit.getEnd(),
				visit.getCustomer().getCustomerId())).thenReturn(false);
		when(visitService.periodOverlapsWithExistingEmployeesVisits(visit.getStart(), visit.getEnd(),
				visit.getEmployee().getEmployeeId())).thenReturn(false);
		sut.validateCreate(visit);
	}
	
	@Test(expected = BusinessValidationException.class)
	public void shouldFailValidationBecouseOfNonexistientCustomer() throws BusinessValidationException {
		when(customerRepo.exists(visit.getCustomer().getCustomerId())).thenReturn(false);
		when(employeeRepo.exists(visit.getEmployee().getEmployeeId())).thenReturn(true);
		when(visitService.periodOverlapsWithExistingCustomerVisits(visit.getStart(), visit.getEnd(),
				visit.getCustomer().getCustomerId())).thenReturn(false);
		when(visitService.periodOverlapsWithExistingEmployeesVisits(visit.getStart(), visit.getEnd(),
				visit.getEmployee().getEmployeeId())).thenReturn(false);
		sut.validateCreate(visit);
	}

	@Test(expected = BusinessValidationException.class)
	public void shouldFailValidationBecouseOfScheduleMismatch() throws BusinessValidationException {
		when(customerRepo.exists(visit.getCustomer().getCustomerId())).thenReturn(true);
		when(employeeRepo.exists(visit.getEmployee().getEmployeeId())).thenReturn(true);
		when(visitService.periodOverlapsWithExistingCustomerVisits(visit.getStart(), visit.getEnd(),
				visit.getCustomer().getCustomerId())).thenReturn(false);
		when(visitService.periodOverlapsWithExistingEmployeesVisits(visit.getStart(), visit.getEnd(),
				visit.getEmployee().getEmployeeId())).thenReturn(false);
		Mockito.doThrow(new BusinessValidationException("")).when(validationHelper)
				.validateAgainstSchedule(visit.getStart(), visit.getEnd(), visit.getEmployee().getEmployeeId());
		sut.validateCreate(visit);
	}

	@Test
	public void ensureShouldBeSuccessful() throws BusinessValidationException {
		when(visitService.exists(visitId)).thenReturn(true);
		sut.ensureThatExists(visitId);
		verify(visitService, times(1)).exists(visitId);
	}

	@Test(expected = BusinessValidationException.class)
	public void ensureShouldFail() throws BusinessValidationException {
		when(visitService.exists(visitId)).thenReturn(false);
		sut.ensureThatExists(visitId);
	}

}
