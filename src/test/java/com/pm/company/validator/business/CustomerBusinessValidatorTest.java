package com.pm.company.validator.business;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pm.company.exception.BusinessValidationException;
import com.pm.company.model.Customer;
import com.pm.company.service.CustomerService;

public class CustomerBusinessValidatorTest {

	@InjectMocks
	private CustomerBusinessValidator sut;
	
	@Mock
	private CustomerService customerSrv;
	
	private Customer customer;
	
	@Before
	public void before(){
		customer = new Customer(1L, "12345678901", "Firstname", "Lastname", new Date());
		sut = new CustomerBusinessValidator();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void validateShouldPass() throws BusinessValidationException{
		when(customerSrv.peselAlreadyInUseInOther(customer.getPesel(), customer.getCustomerId())).thenReturn(false);
		sut.validateUpdate(customer);
		verify(customerSrv, times(1)).peselAlreadyInUseInOther(customer.getPesel(), customer.getCustomerId());
	}
	
	@Test(expected = BusinessValidationException.class)
	public void validateShouldFailBecauseOfPesel() throws BusinessValidationException{
		when(customerSrv.peselAlreadyInUseInOther(customer.getPesel(), customer.getCustomerId())).thenReturn(true);
		sut.validateUpdate(customer);
	}
	
	@Test
	public void ensureThatExistShouldPass() throws BusinessValidationException{
		when(customerSrv.exists(customer.getCustomerId())).thenReturn(true);
		sut.ensureThatExists(customer.getCustomerId());
		verify(customerSrv, times(1)).exists(customer.getCustomerId());
	}
	
	@Test(expected = BusinessValidationException.class)
	public void ensureThatExistShouldFail() throws BusinessValidationException{
		when(customerSrv.exists(customer.getCustomerId())).thenReturn(false);
		sut.ensureThatExists(customer.getCustomerId());
	}
	
}
