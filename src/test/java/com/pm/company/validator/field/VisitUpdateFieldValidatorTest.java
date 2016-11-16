package com.pm.company.validator.field;

import java.util.Date;

import com.pm.company.model.Status;
import com.pm.company.dto.VisitUpdateDTO;
import com.pm.company.exception.CustomValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class VisitUpdateFieldValidatorTest {
	
	private VisitUpdateFieldValidator sut;
	

	@Before
	public void setUp(){
		sut = new VisitUpdateFieldValidator();
	}
	
	public static Object[] provideUsersWithCorrectDates(){
		return new Object[]{
			new Object[]{new VisitUpdateDTO(new Date(1430207200000L), new Date(1430209000000L), true, Status.ACCEPTED)},
			new Object[]{new VisitUpdateDTO(new Date(1430209000000L), new Date(1430209000000L), true, Status.ACCEPTED)}
		};
	}
	
	public static Object[] provideUsersWithIncorrectDates(){
		return new Object[]{
				new Object[]{new VisitUpdateDTO(new Date(1430209000000L), new Date(1430207200000L), true, Status.ACCEPTED)}
		};
	}
	
	@Test
	@Parameters(method = "provideUsersWithCorrectDates")
	public void validShouldPass(VisitUpdateDTO customer) throws CustomValidationException {
		sut.validate(customer);
	}
	
	@Test(expected = CustomValidationException.class)
	@Parameters(method = "provideUsersWithIncorrectDates")
	public void validShoudFailBecouseOfInvalidDatesOrder(VisitUpdateDTO customer) throws CustomValidationException{
		sut.validate(customer);
	}
	
}
