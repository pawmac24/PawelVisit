package com.pm.company.repository;

import com.pm.company.PersistenceContextTest;
import com.pm.company.model.Address;
import com.pm.company.model.Company;
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
public class CompanyRepositoryIntegrationTest {
	
	@Autowired
	private CompanyRepository sut;
	
	private Company company;
	
	@Before
	public void before(){
		Address addressCompany = new Address("city", "postcode", "street", "123");
		company = new Company("companymane", addressCompany);
	}
	
	@Test
	public void testCreate(){
		company = sut.save(company);
		assertNotNull(company);
		assertNotNull(company.getCompanyId());
		Company retrievedCompany = sut.findOne(company.getCompanyId());
		assertNotNull(retrievedCompany);
		assertEquals(company, retrievedCompany);
	}
	
	@Test
	public void testUpdate(){
		String updatedName = "Changedname";
		company = sut.save(company);
		assertNotNull(company);
		assertNotNull(company.getCompanyId());
		company.setName(updatedName);
		Company updatedCompany = sut.save(company);
		assertNotNull(updatedCompany);
		assertEquals(updatedName, updatedCompany.getName());
		
	}
	
	@Test
	public void testDelete(){
		company = sut.save(company);
		assertNotNull(company);
		assertNotNull(company.getCompanyId());
		Long deletedCompanyId = company.getCompanyId();
		sut.delete(deletedCompanyId);
		company = sut.findOne(deletedCompanyId);
		assertNull(company);
	}
}
