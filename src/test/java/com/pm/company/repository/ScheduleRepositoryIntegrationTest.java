package com.pm.company.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;

import com.pm.company.PersistenceContextTest;
import com.pm.company.model.Address;
import com.pm.company.model.Company;
import com.pm.company.model.Department;
import com.pm.company.model.Room;
import com.pm.company.model.Employee;
import com.pm.company.model.Schedule;
import com.pm.company.utils.DateUtils;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(classes = PersistenceContextTest.class, loader=AnnotationConfigContextLoader.class)
@Transactional
public class ScheduleRepositoryIntegrationTest {

	@ClassRule
	public static final SpringClassRule SCR = new SpringClassRule();
	
	@Rule
	public final SpringMethodRule SMR = new SpringMethodRule();
	
	@Autowired
	private ScheduleRepository sut;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private RoomRepository roomRepository;

	private Schedule schedule;
	private Company company;
	private Department department;
	private Employee employee;
	private Room room;

	@Before
	public void before() throws Exception{
		Address addressCompany = new Address("city", "postcode", "street", "123");
		Address addressDepartment = new Address("city", "postcode", "street", "123");

		company = new Company("companymane", addressCompany);
		company = companyRepository.save(company);

		department = new Department("depname", addressDepartment, company);
		department = departmentRepository.save(department);

		room = new Room("123", department);
		room = roomRepository.save(room);

		employee = new Employee("John", "Doe", "Description", department);
		employee = employeeRepository.save(employee);
		
		schedule = new Schedule(
				DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 12, 30)),
				DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 15, 35)),
				"Place 1",
				employee, room);
	}
	
	@Test
	public void testCreate(){
		schedule = sut.save(schedule);
		assertNotNull(schedule);
		assertNotNull(schedule.getScheduleId());
		Schedule retrievedSchedule = sut.findOne(schedule.getScheduleId());
		assertNotNull(retrievedSchedule);
		assertEquals(schedule.getScheduleId(), retrievedSchedule.getScheduleId());
	}
	
	@Test
	public void testUpdate(){
		Date changedStopDate = DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 17, 35));
		schedule = sut.save(schedule);
		assertNotNull(schedule);
		assertNotNull(schedule.getScheduleId());
		schedule.setStop(changedStopDate);
		Schedule updatedSchedule = sut.save(schedule);
		assertNotNull(updatedSchedule);
		assertEquals(changedStopDate, updatedSchedule.getStop());
		
	}
	
	@Test
	public void testDelete(){
		schedule = sut.save(schedule);
		assertNotNull(schedule);
		assertNotNull(schedule.getScheduleId());
		Long deletedScheduleId = schedule.getScheduleId();
		sut.delete(deletedScheduleId);
		schedule = sut.findOne(deletedScheduleId);
		assertNull(schedule);
	}
	
	public static Object[] provideOverlappingTestCases(){
		return new Object[]{
			new Object[]{
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 12, 30)),
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 15, 35)),
					1L},
			new Object[]{
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 11, 30)),
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 16, 35)),
					1L},
			new Object[]{
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 11, 30)),
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 14, 35)),
					1L},
			new Object[]{
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 13, 30)),
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 17, 35)),
					1L},
			new Object[]{
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 16, 30)),
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 17, 35)),
					0L},
			new Object[]{
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 10, 30)),
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 11, 35)),
					0L}
		};
	}
	
	@Test
	@Parameters(method="provideOverlappingTestCases")
	public void testCountOverlapping(Date start, Date stop, Long expectedCount){
		sut.save(schedule);
		Long actualCount = sut.countOverlappingSchedules(start, stop, employee.getEmployeeId());
		assertEquals(expectedCount.longValue(), actualCount.longValue());
	}
	
	@Test
	public void testCountOverlappingOmittingSchedule(){
		schedule = sut.save(schedule);
		Long expectedCount = 0L;
		Long actualCount = sut.countOverlappingSchedulesOmittingSchedule(
				schedule.getStart(), schedule.getStop(),
				schedule.getEmployee().getEmployeeId(), schedule.getScheduleId());
		assertEquals(expectedCount.longValue(), actualCount.longValue());
	}
	
	public static Object[] provideContainingTestCases(){
		return new Object[]{
			new Object[]{
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 12, 30)),
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 15, 35)),
					1L},
			new Object[]{
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 13, 30)),
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 14, 35)),
					1L},
			new Object[]{
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 11, 30)),
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 16, 35)),
					0L},
			new Object[]{
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 11, 30)),
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 14, 35)),
					0L},
			new Object[]{
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 13, 30)),
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 17, 35)),
					0L},
			new Object[]{
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 16, 30)),
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 17, 35)),
					0L},
			new Object[]{
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 10, 30)),
					DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 11, 35)),
					0L}
		};
	}
	
	@Test
	@Parameters(method = "provideContainingTestCases")
	public void testContaining(Date start, Date stop, Long expectedCount){
		sut.save(schedule);
		Long actualCount = sut.countContainingPeriod(start, stop, schedule.getEmployee().getEmployeeId());
		assertEquals(expectedCount.longValue(), actualCount.longValue());
	}
	
}
