package com.pm.company.repository;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceContextTest.class, loader=AnnotationConfigContextLoader.class)
@Transactional
public class DepartmentRepositoryIntegrationTest {
	
	@Autowired
	private DepartmentRepository sut;

	@Autowired
	private CompanyRepository companyRepository;

	private Company company;
	private Department department;

	@Before
	public void before(){
		Address addressCompany = new Address("city", "postcode", "street", "123");
		Address addressDepartment = new Address("citydep", "postcode", "streetdep", "123");

		company = new Company("companymane", addressCompany);
		company = companyRepository.save(company);

		department = new Department("depname", addressDepartment, company);
	}
	
	@Test
	public void testCreate(){
		department = sut.save(department);
		assertNotNull(department);
		assertNotNull(department.getDepartmentId());
		Department retrievedDepartment = sut.findOne(department.getDepartmentId());
		assertNotNull(retrievedDepartment);
		assertEquals(department, retrievedDepartment);
	}
	
	@Test
	public void testUpdate(){
		String updatedName = "Changedname";
		department = sut.save(department);
		assertNotNull(department);
		assertNotNull(department.getDepartmentId());
		department.setName(updatedName);
		Department updatedDepartment = sut.save(department);
		assertNotNull(updatedDepartment);
		assertEquals(updatedName, updatedDepartment.getName());
		
	}
	
	@Test
	public void testDelete(){
		department = sut.save(department);
		assertNotNull(department);
		assertNotNull(department.getDepartmentId());
		Long deletedDepartmentId = department.getDepartmentId();
		sut.delete(deletedDepartmentId);
		department = sut.findOne(deletedDepartmentId);
		assertNull(department);
	}
}
