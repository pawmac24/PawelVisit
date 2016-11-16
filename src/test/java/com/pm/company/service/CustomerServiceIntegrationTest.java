package com.pm.company.service;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;

import com.pm.company.PersistenceContextTest;
import com.pm.company.dto.primarykey.CustomerPrimaryKeyDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.pm.company.dto.CustomerCreateDTO;
import com.pm.company.dto.CustomerUpdateDTO;
import com.pm.company.dto.result.CustomerResultDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.utils.DateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceContextTest.class, loader=AnnotationConfigContextLoader.class)
@Transactional
public class CustomerServiceIntegrationTest {

	@Autowired
	private CustomerService sut;
	
	private CustomerCreateDTO customerCrtDTO;
	private Long arbitraryFalseCustomerId;
	
	@Before
	public void before(){
		arbitraryFalseCustomerId = 3423432423409232L;
		customerCrtDTO = new CustomerCreateDTO("99920101225",
				"Christina", "Yanda", DateUtils.asDate(LocalDate.of(1992, Month.JANUARY, 1)));
	}
	
	@Test
	public void existShouldReturnFalse(){
		
		assertFalse(sut.exists(arbitraryFalseCustomerId));
	}
	
	@Test
	public void createShouldSuccedReturningId() throws BusinessValidationException{
		CustomerPrimaryKeyDTO customerPKDTO = sut.create(customerCrtDTO);
		assertNotNull(customerPKDTO);
		assertNotNull(customerPKDTO.getId());
	}
	
	@Test(expected = BusinessValidationException.class)
	public void createShouldFailBecouseOfPeselUniqenessViolation() throws BusinessValidationException{
		sut.create(customerCrtDTO);
		sut.create(customerCrtDTO);
	}
	
	@Test
	public void updateShouldSucced() throws BusinessValidationException{
		CustomerPrimaryKeyDTO customerPKDTO = sut.create(customerCrtDTO);
		assertNotNull(customerPKDTO);
		assertNotNull(customerPKDTO.getId());
		CustomerUpdateDTO customerUpdtDTO = new CustomerUpdateDTO("Mila", "Yankova");
		sut.update(customerPKDTO.getId(), customerUpdtDTO);
		CustomerResultDTO retrievedCustomer = sut.find(customerPKDTO.getId());
		assertNotNull(retrievedCustomer);
		assertEquals(customerUpdtDTO.getFirstName(), retrievedCustomer.getFirstName());
		assertEquals(customerUpdtDTO.getLastName(), retrievedCustomer.getLastName());
	}
	
	@Test
	public void deleteShouldSucced() throws BusinessValidationException{
		CustomerPrimaryKeyDTO customerPKDTO = sut.create(customerCrtDTO);
		assertNotNull(customerPKDTO);
		assertNotNull(customerPKDTO.getId());
		sut.delete(customerPKDTO.getId());
		assertFalse(sut.exists(customerPKDTO.getId()));
	}
	
	@Test(expected = BusinessValidationException.class)
	public void deleteShouldFail() throws BusinessValidationException{
		sut.delete(arbitraryFalseCustomerId);
	}
}
