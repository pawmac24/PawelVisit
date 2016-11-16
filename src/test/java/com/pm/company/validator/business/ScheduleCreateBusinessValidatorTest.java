package com.pm.company.validator.business;

import java.util.Date;

import com.pm.company.model.Room;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.model.Employee;
import com.pm.company.model.Schedule;
import com.pm.company.service.ScheduleService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import com.pm.company.repository.EmployeeRepository;

public class ScheduleCreateBusinessValidatorTest {

	@InjectMocks
	private ScheduleBusinessValidator sut;

	@Mock
	private ScheduleService scheduleService;
	
	@Mock
	private EmployeeRepository employeeRepo;

	private Schedule schedule;
	
	private Long scheduleId;

	@Before
	public void before() {
		schedule = new Schedule(new Date(), new Date(), "Place", new Employee(), new Room());
		scheduleId = 1L;
		sut = new ScheduleBusinessValidator();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldPassValidation() throws BusinessValidationException {
		when(scheduleService.periodOverlapsWithExistingSchedules(schedule.getStart(), schedule.getStop(),
				schedule.getEmployee().getEmployeeId())).thenReturn(false);
		when(employeeRepo.exists(schedule.getEmployee().getEmployeeId())).thenReturn(true);
		sut.validateCreate(schedule);
		verify(scheduleService, times(1)).periodOverlapsWithExistingSchedules(schedule.getStart(), schedule.getStop(),
				schedule.getEmployee().getEmployeeId());
	}

	@Test(expected = BusinessValidationException.class)
	public void shouldFailValidationBecauseOfOverlaps() throws BusinessValidationException {
		when(scheduleService.periodOverlapsWithExistingSchedules(schedule.getStart(), schedule.getStop(),
				schedule.getEmployee().getEmployeeId())).thenReturn(true);
		when(employeeRepo.exists(schedule.getEmployee().getEmployeeId())).thenReturn(true);
		sut.validateCreate(schedule);
	}
	
	@Test(expected = BusinessValidationException.class)
	public void shouldFailValidationBecauseOfNonexistentEmployee() throws BusinessValidationException {
		when(scheduleService.periodOverlapsWithExistingSchedules(schedule.getStart(), schedule.getStop(),
				schedule.getEmployee().getEmployeeId())).thenReturn(false);
		when(employeeRepo.exists(schedule.getEmployee().getEmployeeId())).thenReturn(false);
		sut.validateCreate(schedule);
	}
	
	@Test
	public void ensureShouldBeSuccessful() throws BusinessValidationException{
		when(scheduleService.exists(scheduleId)).thenReturn(true);
		sut.ensureThatExists(scheduleId);
		verify(scheduleService, times(1)).exists(scheduleId);
	}
	
	@Test(expected = BusinessValidationException.class)
	public void ensureShouldFail() throws BusinessValidationException{
		when(scheduleService.exists(scheduleId)).thenReturn(false);
		sut.ensureThatExists(scheduleId);
	}
	
}
