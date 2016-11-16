package com.pm.company.validator.business;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import com.pm.company.exception.BusinessValidationException;
import com.pm.company.model.Customer;
import com.pm.company.model.Employee;
import com.pm.company.model.Visit;
import com.pm.company.service.VisitService;
import com.pm.company.validator.helper.VisitValidationHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class VisitBusinessValidatorTest {

	@InjectMocks
	private VisitBusinessValidator sut;

	@Mock
	private VisitService visitService;

	@Mock
	private VisitValidationHelper validationHelper;

	private Visit visit;

	@Before
	public void before() {
		visit = new Visit(1L, new Date(), new Date(), true, new Customer(), new Employee(), 1);
		sut = new VisitBusinessValidator();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldPassValidation() throws BusinessValidationException {
		when(visitService.periodOverlapsWithOtherExistingCustomersVisits(visit.getStart(), visit.getEnd(),
				visit.getCustomer().getCustomerId(), visit.getVisitId())).thenReturn(false);
		when(visitService.periodOverlapsWithOtherExistingEmployeesVisits(visit.getStart(), visit.getEnd(),
				visit.getEmployee().getEmployeeId(), visit.getVisitId())).thenReturn(false);
		sut.validateUpdate(visit);
		verify(visitService, times(1)).periodOverlapsWithOtherExistingCustomersVisits(visit.getStart(), visit.getEnd(),
				visit.getCustomer().getCustomerId(), visit.getVisitId());
		verify(visitService, times(1)).periodOverlapsWithOtherExistingEmployeesVisits(visit.getStart(), visit.getEnd(),
				visit.getEmployee().getEmployeeId(), visit.getVisitId());
	}

	@Test(expected = BusinessValidationException.class)
	public void shouldFailValidationBecauseOfCustomerOverlaps() throws BusinessValidationException {
		when(visitService.periodOverlapsWithOtherExistingCustomersVisits(visit.getStart(), visit.getEnd(),
				visit.getCustomer().getCustomerId(), visit.getVisitId())).thenReturn(true);
		when(visitService.periodOverlapsWithOtherExistingEmployeesVisits(visit.getStart(), visit.getEnd(),
				visit.getEmployee().getEmployeeId(), visit.getVisitId())).thenReturn(false);
		sut.validateUpdate(visit);
	}

	@Test(expected = BusinessValidationException.class)
	public void shouldFailValidationBecauseOfEmployeeOverlaps() throws BusinessValidationException {
		when(visitService.periodOverlapsWithOtherExistingCustomersVisits(visit.getStart(), visit.getEnd(),
				visit.getCustomer().getCustomerId(), visit.getVisitId())).thenReturn(false);
		when(visitService.periodOverlapsWithOtherExistingEmployeesVisits(visit.getStart(), visit.getEnd(),
				visit.getEmployee().getEmployeeId(), visit.getVisitId())).thenReturn(true);
		sut.validateUpdate(visit);
	}

	@Test(expected = BusinessValidationException.class)
	public void shouldFailValidationBecauseOfScheduleMismatch() throws BusinessValidationException {
		when(visitService.periodOverlapsWithOtherExistingCustomersVisits(visit.getStart(), visit.getEnd(),
				visit.getCustomer().getCustomerId(), visit.getVisitId())).thenReturn(false);
		when(visitService.periodOverlapsWithOtherExistingEmployeesVisits(visit.getStart(), visit.getEnd(),
				visit.getEmployee().getEmployeeId(), visit.getVisitId())).thenReturn(false);
		Mockito.doThrow(new BusinessValidationException("")).when(validationHelper)
				.validateAgainstSchedule(visit.getStart(), visit.getEnd(), visit.getEmployee().getEmployeeId());
		sut.validateUpdate(visit);
	}

	@Test
	public void ensureShouldBeSuccessful() throws BusinessValidationException {
		when(visitService.exists(visit.getVisitId())).thenReturn(true);
		sut.ensureThatExists(visit.getVisitId());
		verify(visitService, times(1)).exists(visit.getVisitId());
	}

	@Test(expected = BusinessValidationException.class)
	public void ensureShouldFail() throws BusinessValidationException {
		when(visitService.exists(visit.getVisitId())).thenReturn(false);
		sut.ensureThatExists(visit.getVisitId());
	}

}
