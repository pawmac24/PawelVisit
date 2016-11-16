package com.pm.company.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pm.company.dto.ScheduleCreateDTO;
import com.pm.company.dto.ScheduleUpdateDTO;
import com.pm.company.dto.primarykey.SchedulePrimaryKeyDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.model.Employee;
import com.pm.company.model.Room;
import com.pm.company.model.Schedule;
import com.pm.company.repository.EmployeeRepository;
import com.pm.company.repository.RoomRepository;
import com.pm.company.repository.ScheduleRepository;
import com.pm.company.utils.DateUtils;
import com.pm.company.validator.business.BusinessValidator;
import junitparams.Parameters;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import junitparams.JUnitParamsRunner;

import java.time.LocalDateTime;
import java.time.Month;

@RunWith(JUnitParamsRunner.class)
public class ScheduleServiceImplTest {

	@InjectMocks
	private ScheduleService sut;

	@Mock
	private ScheduleRepository scheduleRepository;

	@Mock
	private EmployeeRepository employeeRepo;

	@Mock
	private RoomRepository roomRepository;

	@Mock
	private BusinessValidator<Schedule, Long> scheduleValidator;

//	@Mock
//	private BusinessValidator<ScheduleCreateDTO, Long> scheduleCrtValidator;

	@Mock
	private Mapper mapper;

	@Before
	public void before(){
		sut= new ScheduleServiceImpl();
		MockitoAnnotations.initMocks(this);
	}

	private Object[] provideCreateDTO_SchedulePairs(){
		ScheduleCreateDTO scheduleCreateDTO1 = new ScheduleCreateDTO(
				DateUtils.asDate(LocalDateTime.of(2005, Month.JANUARY, 2, 10, 00)),
				DateUtils.asDate(LocalDateTime.of(2005, Month.JANUARY, 2, 18, 00)), "P1", 1L, 1L);
		Schedule scheduleExpected1 = new Schedule(1L,
				scheduleCreateDTO1.getStart(), scheduleCreateDTO1.getStop(),
				scheduleCreateDTO1.getPlace(), new Employee(), new Room());

		ScheduleCreateDTO scheduleCreateDTO2 = new ScheduleCreateDTO(
				DateUtils.asDate(LocalDateTime.of(2005, Month.JANUARY, 3, 8, 00)),
				DateUtils.asDate(LocalDateTime.of(2005, Month.JANUARY, 3, 16, 00)), "P1", 1L, 2L);
		Schedule scheduleExpected2 = new Schedule(2L,
				scheduleCreateDTO2.getStart(), scheduleCreateDTO2.getStop(),
				scheduleCreateDTO2.getPlace(), new Employee(), new Room());

		ScheduleCreateDTO scheduleCreateDTO3 = new ScheduleCreateDTO(
				DateUtils.asDate(LocalDateTime.of(2005, Month.JANUARY, 4, 14, 00)),
				DateUtils.asDate(LocalDateTime.of(2005, Month.JANUARY, 4, 19, 00)), "P1", 1L, 3L);
		Schedule scheduleExpected3 = new Schedule(3L,
				scheduleCreateDTO3.getStart(), scheduleCreateDTO3.getStop(),
				scheduleCreateDTO3.getPlace(), new Employee(), new Room());

		return new Object[]{
				new Object[]{ scheduleCreateDTO1, scheduleExpected1, },
				new Object[]{ scheduleCreateDTO2, scheduleExpected2, },
				new Object[]{ scheduleCreateDTO3, scheduleExpected3 }
		};
	}

	private Object[] provideUpdateDTO_SchedulePairs(){
		ScheduleUpdateDTO scheduleCreateDTO1 = new ScheduleUpdateDTO(
				DateUtils.asDate(LocalDateTime.of(2005, Month.JANUARY, 2, 10, 00)),
				DateUtils.asDate(LocalDateTime.of(2005, Month.JANUARY, 2, 18, 00)), "P1");
		Schedule scheduleExpected1 = new Schedule(1L,
				scheduleCreateDTO1.getStart(), scheduleCreateDTO1.getStop(),
				scheduleCreateDTO1.getPlace(), new Employee(), new Room());

		ScheduleUpdateDTO scheduleCreateDTO2 = new ScheduleUpdateDTO(
				DateUtils.asDate(LocalDateTime.of(2005, Month.JANUARY, 3, 8, 00)),
				DateUtils.asDate(LocalDateTime.of(2005, Month.JANUARY, 3, 16, 00)), "P1");
		Schedule scheduleExpected2 = new Schedule(2L,
				scheduleCreateDTO2.getStart(), scheduleCreateDTO2.getStop(),
				scheduleCreateDTO2.getPlace(), new Employee(), new Room());

		ScheduleUpdateDTO scheduleCreateDTO3 = new ScheduleUpdateDTO(
				DateUtils.asDate(LocalDateTime.of(2005, Month.JANUARY, 4, 14, 00)),
				DateUtils.asDate(LocalDateTime.of(2005, Month.JANUARY, 4, 19, 00)), "P1");
		Schedule scheduleExpected3 = new Schedule(3L,
				scheduleCreateDTO3.getStart(), scheduleCreateDTO3.getStop(),
				scheduleCreateDTO3.getPlace(), new Employee(), new Room());

		return new Object[]{
				new Object[]{ scheduleCreateDTO1, scheduleExpected1, },
				new Object[]{ scheduleCreateDTO2, scheduleExpected2, },
				new Object[]{ scheduleCreateDTO3, scheduleExpected3 }
		};
	}


	@Test
	@Parameters(method = "provideCreateDTO_SchedulePairs")
	public void testCreateSchedules(ScheduleCreateDTO scheduleInput, Schedule scheduleExpected) throws BusinessValidationException {
		when(employeeRepo.findOne(scheduleInput.getEmployeeId())).thenReturn(new Employee());
		when(roomRepository.findOne(scheduleInput.getRoomId())).thenReturn(new Room());
		when(mapper.map(scheduleInput, Schedule.class)).thenReturn(new Schedule());
		when(scheduleRepository.save(any(Schedule.class))).thenReturn(scheduleExpected);

		SchedulePrimaryKeyDTO schedulePrimaryKeyDTO = sut.create(scheduleInput);

		verify(scheduleValidator, times(1)).validateCreate(any(Schedule.class));
		verify(employeeRepo, times(1)).findOne(scheduleInput.getEmployeeId());
		verify(roomRepository, times(1)).findOne(scheduleInput.getRoomId());
		verify(scheduleRepository, times(1)).save(any(Schedule.class));
		assertNotNull(schedulePrimaryKeyDTO.getId());
		assertEquals(scheduleExpected.getScheduleId().longValue(), schedulePrimaryKeyDTO.getId().longValue());
	}

	@Test(expected = BusinessValidationException.class)
	@Parameters(method = "provideCreateDTO_SchedulePairs")
	public void testCreateSchedulesShouldFailBecauseOfValidaton(ScheduleCreateDTO scheduleInput, Schedule scheduleExpected) throws BusinessValidationException {
		when(employeeRepo.findOne(scheduleInput.getEmployeeId())).thenReturn(new Employee());
		when(roomRepository.findOne(scheduleInput.getRoomId())).thenReturn(new Room());
		when(mapper.map(scheduleInput, Schedule.class)).thenReturn(new Schedule());
		Mockito.doThrow(new BusinessValidationException("")).when(scheduleValidator).validateCreate(any(Schedule.class));
		when(scheduleRepository.save(any(Schedule.class))).thenReturn(scheduleExpected);

		sut.create(scheduleInput);
	}

	@Test
	@Parameters(method = "provideUpdateDTO_SchedulePairs")
	public void testUpdateSchedules(ScheduleUpdateDTO scheduleInput, Schedule scheduleExpected) throws BusinessValidationException {
		when(scheduleRepository.findOne(scheduleExpected.getScheduleId())).thenReturn(scheduleExpected);
		when(scheduleRepository.save(any(Schedule.class))).thenReturn(scheduleExpected);

		sut.update(scheduleInput, scheduleExpected.getScheduleId());

		verify(scheduleRepository, times(1)).save(any(Schedule.class));
		verify(scheduleValidator, times(1)).ensureThatExists(scheduleExpected.getScheduleId());
		verify(scheduleValidator, times(1)).validateUpdate(scheduleExpected);
	}

	@Test(expected = BusinessValidationException.class)
	@Parameters(method = "provideUpdateDTO_SchedulePairs")
	public void testUpdateSchedulesShouldFailBecauseOfNotexistingSchedule(ScheduleUpdateDTO scheduleInput, Schedule scheduleExpected) throws BusinessValidationException {
		when(scheduleRepository.findOne(scheduleExpected.getScheduleId())).thenReturn(scheduleExpected);
		when(scheduleRepository.save(any(Schedule.class))).thenReturn(scheduleExpected);
		Mockito.doThrow(new BusinessValidationException("")).when(scheduleValidator).ensureThatExists(scheduleExpected.getScheduleId());

		sut.update(scheduleInput, scheduleExpected.getScheduleId());
	}

	@Test(expected = BusinessValidationException.class)
	@Parameters(method = "provideUpdateDTO_SchedulePairs")
	public void testUpdateSchedulesShouldFailBecauseOfValidation(ScheduleUpdateDTO scheduleInput, Schedule scheduleExpected) throws BusinessValidationException {
		when(scheduleRepository.findOne(scheduleExpected.getScheduleId())).thenReturn(scheduleExpected);
		when(scheduleRepository.save(any(Schedule.class))).thenReturn(scheduleExpected);
		Mockito.doThrow(new BusinessValidationException("")).when(scheduleValidator).validateUpdate(scheduleExpected);

		sut.update(scheduleInput, scheduleExpected.getScheduleId() );
	}

}
