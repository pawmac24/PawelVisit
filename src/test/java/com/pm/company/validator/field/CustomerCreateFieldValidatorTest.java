package com.pm.company.validator.field;

import static org.mockito.Mockito.when;

import java.util.Date;

import com.pm.company.exception.CustomValidationException;
import com.pm.company.validator.helper.FieldValidationHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pm.company.dto.CustomerCreateDTO;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class CustomerCreateFieldValidatorTest {
	
	@InjectMocks
	private CustomerCreateFieldValidator sut;
	
	@Mock
	private FieldValidationHelper validationHelper;

	@Before
	public void setUp(){
		sut = new CustomerCreateFieldValidator();
		MockitoAnnotations.initMocks(this);
	}
	
	public static Object[] provideUsersWithCorrectPesel(){
		return new Object[]{
			new Object[]{new CustomerCreateDTO("12345678901", "John", "Doe", new Date())},
			new Object[]{new CustomerCreateDTO("19565678901", "Mary", "Jane", new Date())},
			new Object[]{new CustomerCreateDTO("20005678901", "Luise", "van Hagen", new Date())}
		};
	}
	
	public static Object[] provideUsersWithIncorrectPesel(){
		return new Object[]{
			new Object[]{new CustomerCreateDTO("12c45678901", "John", "Doe", new Date())},
			new Object[]{new CustomerCreateDTO("v9565678901", "Mary", "Jane", new Date())},
			new Object[]{new CustomerCreateDTO("2000567890d", "Luise", "van Hagen", new Date())}
		};
	}
	
	@Test
	@Parameters(method = "provideUsersWithCorrectPesel")
	public void validShouldPass(CustomerCreateDTO customer) throws CustomValidationException {
		when(validationHelper.isTextOnly(customer.getFirstName())).thenReturn(true);
		when(validationHelper.isTextOnly(customer.getLastName())).thenReturn(true);
		sut.validate(customer);
	}
	
	@Test(expected = CustomValidationException.class)
	@Parameters(method = "provideUsersWithIncorrectPesel")
	public void validShoudFailBecouseOfInvalidPesel(CustomerCreateDTO customer) throws CustomValidationException{
		when(validationHelper.isTextOnly(customer.getFirstName())).thenReturn(true);
		when(validationHelper.isTextOnly(customer.getLastName())).thenReturn(true);
		sut.validate(customer);
	}
	
	@Test(expected = CustomValidationException.class)
	@Parameters(method = "provideUsersWithCorrectPesel")
	public void validShoudFailBecouseOfInvalidFirstName(CustomerCreateDTO customer) throws CustomValidationException{
		when(validationHelper.isTextOnly(customer.getFirstName())).thenReturn(false);
		when(validationHelper.isTextOnly(customer.getLastName())).thenReturn(true);
		sut.validate(customer);
	}
	
	@Test(expected = CustomValidationException.class)
	@Parameters(method = "provideUsersWithCorrectPesel")
	public void validShoudFailBecouseOfInvalidLastName(CustomerCreateDTO customer) throws CustomValidationException{
		when(validationHelper.isTextOnly(customer.getFirstName())).thenReturn(true);
		when(validationHelper.isTextOnly(customer.getLastName())).thenReturn(false);
		sut.validate(customer);
	}
}
