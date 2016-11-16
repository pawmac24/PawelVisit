package com.pm.company.validator.business;

import java.util.Date;

import com.pm.company.exception.BusinessValidationException;
import com.pm.company.model.Customer;
import com.pm.company.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerCreateBusinessValidatorTest {

	@InjectMocks
	private CustomerBusinessValidator sut;
	
	@Mock
	private CustomerService customerSrv;
	
	private Customer customer;
	
	private Long customerId;
	
	@Before
	public void before(){
		customer = new Customer("12345678901", "Firstname", "Lastname", new Date());
		customerId = 1L;
		sut = new CustomerBusinessValidator();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void validateShouldPass() throws BusinessValidationException {
		when(customerSrv.peselAlreadyInUse(customer.getPesel())).thenReturn(false);
		sut.validateCreate(customer);
		verify(customerSrv, times(1)).peselAlreadyInUse(customer.getPesel());
	}
	
	@Test(expected = BusinessValidationException.class)
	public void validateShouldFailBecouseOfPesel() throws BusinessValidationException{
		when(customerSrv.peselAlreadyInUse(customer.getPesel())).thenReturn(true);
		sut.validateCreate(customer);
	}
	
	@Test
	public void ensureThatExistShouldPass() throws BusinessValidationException{
		when(customerSrv.exists(customerId)).thenReturn(true);
		sut.ensureThatExists(customerId);
		verify(customerSrv, times(1)).exists(customerId);
	}
	
	@Test(expected = BusinessValidationException.class)
	public void ensureThatExistShouldFail() throws BusinessValidationException{
		when(customerSrv.exists(customerId)).thenReturn(false);
		sut.ensureThatExists(customerId);
	}
	
}
