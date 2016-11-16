package com.pm.company.validator.business;

import java.util.Date;

import com.pm.company.model.Room;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.service.ScheduleService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import com.pm.company.model.Employee;
import com.pm.company.model.Schedule;

public class ScheduleBusinessValidatorTest {

	@InjectMocks
	private ScheduleBusinessValidator sut;

	@Mock
	private ScheduleService scheduleService;

	private Schedule schedule;

	@Before
	public void before() {
		schedule = new Schedule(1L, new Date(), new Date(), "Place", new Employee(), new Room());
		sut = new ScheduleBusinessValidator();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldPassValidation() throws BusinessValidationException {
		when(scheduleService.periodOverlapsWithOtherExistingSchedules(schedule.getStart(), schedule.getStop(),
				schedule.getEmployee().getEmployeeId(), schedule.getScheduleId())).thenReturn(false);
		sut.validateUpdate(schedule);
		verify(scheduleService, times(1)).periodOverlapsWithOtherExistingSchedules(schedule.getStart(), schedule.getStop(),
				schedule.getEmployee().getEmployeeId(), schedule.getScheduleId());
	}

	@Test(expected = BusinessValidationException.class)
	public void shouldFailValidationBecouseOfOverlaps() throws BusinessValidationException {
		when(scheduleService.periodOverlapsWithOtherExistingSchedules(schedule.getStart(), schedule.getStop(),
				schedule.getEmployee().getEmployeeId(), schedule.getScheduleId())).thenReturn(true);
		sut.validateUpdate(schedule);
	}
	
	@Test
	public void ensureShouldBeSuccessful() throws BusinessValidationException{
		when(scheduleService.exists(schedule.getScheduleId())).thenReturn(true);
		sut.ensureThatExists(schedule.getScheduleId());
		verify(scheduleService, times(1)).exists(schedule.getScheduleId());
	}
	
	@Test(expected = BusinessValidationException.class)
	public void ensureShouldFail() throws BusinessValidationException{
		when(scheduleService.exists(schedule.getScheduleId())).thenReturn(false);
		sut.ensureThatExists(schedule.getScheduleId());
	}
	
}
