package com.pm.company.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.time.Month;

import com.pm.company.PersistenceContextTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.pm.company.model.Customer;
import com.pm.company.utils.DateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceContextTest.class, loader=AnnotationConfigContextLoader.class)
@Transactional
public class CustomerRepositoryIntegrationTest {
	
	@Autowired
	private CustomerRepository sut;
	
	private Customer customer;
	
	@Before
	public void before(){
		customer = new Customer("99990101091", "John", "Malkovich", DateUtils.asDate(LocalDate.of(1997, Month.SEPTEMBER, 5)));
	}
	
	@Test
	public void testCreate(){
		customer = sut.save(customer);
		assertNotNull(customer);
		assertNotNull(customer.getCustomerId());
		Customer retrievedCustomer = sut.findOne(customer.getCustomerId());
		assertNotNull(retrievedCustomer);
		assertEquals(customer, retrievedCustomer);
	}
	
	@Test
	public void testUpdate(){
		String updatedName = "Changedname";
		customer = sut.save(customer);
		assertNotNull(customer);
		assertNotNull(customer.getCustomerId());
		customer.setFirstName(updatedName);
		Customer updatedCustomer = sut.save(customer);
		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getFirstName());
		
	}
	
	@Test
	public void testDelete(){
		customer = sut.save(customer);
		assertNotNull(customer);
		assertNotNull(customer.getCustomerId());
		Long deletedCustomerId = customer.getCustomerId();
		sut.delete(deletedCustomerId);
		customer = sut.findOne(deletedCustomerId);
		assertNull(customer);
	}
	
	@Test
	public void testCountByPesel(){
		Long peselCntBeforeInsert = sut.countByPesel(customer.getPesel());
		customer = sut.save(customer);
		assertNotNull(customer);
		assertNotNull(customer.getCustomerId());
		Long peselCntAfterInsert = sut.countByPesel(customer.getPesel());
		
		assertEquals(0L, peselCntBeforeInsert.longValue());
		assertEquals(1L, peselCntAfterInsert.longValue());
	}
	
	@Test
	public void testCountByPeselOmmitingCustomer(){
		customer = sut.save(customer);
		assertNotNull(customer);
		assertNotNull(customer.getCustomerId());
		Long peselCnt = sut.countByPeselAndCustomerIdNot(
				customer.getPesel(), customer.getCustomerId());
		
		assertEquals(0L, peselCnt.longValue());
	}
}
