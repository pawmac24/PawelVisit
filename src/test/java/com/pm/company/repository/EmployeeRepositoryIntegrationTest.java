package com.pm.company.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.pm.company.PersistenceContextTest;
import com.pm.company.model.Address;
import com.pm.company.model.Company;
import com.pm.company.model.Department;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.pm.company.model.Employee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceContextTest.class, loader=AnnotationConfigContextLoader.class)
@Transactional
public class EmployeeRepositoryIntegrationTest {

	@Autowired
	private EmployeeRepository sut;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private CompanyRepository companyRepository;

	private Company company;
	private Department department;
	private Employee employee;

	@Before
	public void before(){
		Address addressCompany = new Address("city", "postcode", "street", "123");
		Address addressDepartment = new Address("city", "postcode", "street", "123");

		company = new Company("companymane", addressCompany);
		company = companyRepository.save(company);

		department = new Department("depname", addressDepartment, company);
		department = departmentRepository.save(department);

		employee = new Employee("John", "Doe", "Description", department);
	}
	
	@Test
	public void testCreate(){
		employee = sut.save(employee);
		assertNotNull(employee);
		assertNotNull(employee.getEmployeeId());
		Employee retrievedEmployee = sut.findOne(employee.getEmployeeId());
		assertNotNull(retrievedEmployee);
		assertEquals(employee, retrievedEmployee);
	}
	
	@Test
	public void testUpdate(){
		String updatedName = "Changedname";
		employee = sut.save(employee);
		assertNotNull(employee);
		assertNotNull(employee.getEmployeeId());
		employee.setFirstName(updatedName);
		Employee updatedEmployee = sut.save(employee);
		assertNotNull(updatedEmployee);
		assertEquals(updatedName, updatedEmployee.getFirstName());
		
	}
	
	@Test
	public void testDelete(){
		employee = sut.save(employee);
		assertNotNull(employee);
		assertNotNull(employee.getEmployeeId());
		Long deletedEmployeeId = employee.getEmployeeId();
		sut.delete(deletedEmployeeId);
		employee = sut.findOne(deletedEmployeeId);
		assertNull(employee);
	}
	
}
