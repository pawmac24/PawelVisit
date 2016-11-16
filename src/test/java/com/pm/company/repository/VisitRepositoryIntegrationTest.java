package com.pm.company.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;

import com.pm.company.PersistenceContextTest;
import com.pm.company.model.Address;
import com.pm.company.model.Company;
import com.pm.company.model.Department;
import com.pm.company.model.Visit;
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

import com.pm.company.model.Customer;
import com.pm.company.model.Employee;
import com.pm.company.utils.DateUtils;

import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(classes = PersistenceContextTest.class, loader=AnnotationConfigContextLoader.class)
@Transactional
public class VisitRepositoryIntegrationTest {
	
	@ClassRule
	public static final SpringClassRule SCR = new SpringClassRule();
	
	@Rule
	public final SpringMethodRule SMR = new SpringMethodRule(); 
	
	@Autowired
	private VisitRepository sut;
	
	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private EmployeeRepository employeeRepo;
	
	private Visit visit;
	private Company company;
	private Department department;
	private Employee employee;
	private Customer customer;
	
	@Before
	public void before() throws Exception{
		Address addressCompany = new Address("city", "postcode", "street", "123");
		Address addressDepartment = new Address("city", "postcode", "street", "123");

		company = new Company("companymane", addressCompany);
		company = companyRepository.save(company);

		department = new Department("depname", addressDepartment, company);
		department = departmentRepository.save(department);

		employee = new Employee("John", "Doe", "Description", department);
		employee = employeeRepo.save(employee);
		
		customer = new Customer("99990101256", "Maria", "Gonzalez",
				DateUtils.asDate(LocalDate.of(1995, Month.APRIL, 19)));
		customer = customerRepo.save(customer);
		
		visit = new Visit(
				DateUtils.asDate(LocalDateTime.of(2015, Month.JULY, 12, 10, 15)),
				DateUtils.asDate(LocalDateTime.of(2015, Month.JULY, 12, 12, 15)),
				true, customer, employee, 1);
	}
	
	@Test
	public void testCreate(){
		visit = sut.save(visit);
		assertNotNull(visit);
		assertNotNull(visit.getVisitId());
		Visit retrievedVisit = sut.findOne(visit.getVisitId());
		assertNotNull(retrievedVisit);
		assertEquals(visit.getVisitId(), retrievedVisit.getVisitId());
	}
	
	@Test
	public void testUpdate(){
		Date changedEndDate = DateUtils.asDate(LocalDateTime.of(2015, Month.OCTOBER, 30, 17, 35));
		visit = sut.save(visit);
		assertNotNull(visit);
		assertNotNull(visit.getVisitId());
		visit.setEnd(changedEndDate);
		Visit updatedVisit = sut.save(visit);
		assertNotNull(updatedVisit);
		assertEquals(changedEndDate, updatedVisit.getEnd());
		
	}
	
	@Test
	public void testDelete(){
		visit = sut.save(visit);
		assertNotNull(visit);
		assertNotNull(visit.getVisitId());
		Long deletedVisitId = visit.getVisitId();
		sut.delete(deletedVisitId);
		visit = sut.findOne(deletedVisitId);
		assertNull(visit);
	}
	
}
