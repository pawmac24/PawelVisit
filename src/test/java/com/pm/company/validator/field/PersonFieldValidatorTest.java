package com.pm.company.validator.field;

import static org.mockito.Mockito.when;

import com.pm.company.dto.CustomerUpdateDTO;
import com.pm.company.exception.CustomValidationException;
import com.pm.company.model.Person;
import com.pm.company.validator.helper.FieldValidationHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class PersonFieldValidatorTest {
	
	@InjectMocks
	private PersonFieldValidator sut;
	
	@Mock
	private FieldValidationHelper validationHelper;

	@Before
	public void setUp(){
		sut = new PersonFieldValidator();
		MockitoAnnotations.initMocks(this);
	}
	
	public static Object[] provideUsers(){
		return new Object[]{
			new Object[]{new CustomerUpdateDTO("John", "Doe")},
			new Object[]{new CustomerUpdateDTO("Mary", "Jane")},
			new Object[]{new CustomerUpdateDTO("Luise", "van Hagen")}
		};
	}
	
	@Test
	@Parameters(method = "provideUsers")
	public void validShouldPass(Person person) throws CustomValidationException {
		when(validationHelper.isTextOnly(person.getFirstName())).thenReturn(true);
		when(validationHelper.isTextOnly(person.getLastName())).thenReturn(true);
		sut.validate(person);
	}
	
	@Test(expected = CustomValidationException.class)
	@Parameters(method = "provideUsers")
	public void validShoudFailBecouseOfInvalidFirstName(Person person) throws CustomValidationException{
		when(validationHelper.isTextOnly(person.getFirstName())).thenReturn(false);
		when(validationHelper.isTextOnly(person.getLastName())).thenReturn(true);
		sut.validate(person);
	}
	
	@Test(expected = CustomValidationException.class)
	@Parameters(method = "provideUsers")
	public void validShoudFailBecouseOfInvalidLastName(Person person) throws CustomValidationException{
		when(validationHelper.isTextOnly(person.getFirstName())).thenReturn(true);
		when(validationHelper.isTextOnly(person.getLastName())).thenReturn(false);
		sut.validate(person);
	}
}
