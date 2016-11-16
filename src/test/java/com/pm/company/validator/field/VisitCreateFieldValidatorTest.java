package com.pm.company.validator.field;

import java.util.Date;

import com.pm.company.model.Status;
import com.pm.company.exception.CustomValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.pm.company.dto.VisitCreateDTO;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class VisitCreateFieldValidatorTest {
	
	private VisitCreateFieldValidator sut;
	

	@Before
	public void setUp(){
		sut = new VisitCreateFieldValidator();
	}
	
	public static Object[] provideUsersWithCorrectDates(){
		return new Object[]{
			new Object[]{new VisitCreateDTO(new Date(1430207200000L), new Date(1430209000000L), true, 1L, 1L, Status.NEW)},
			new Object[]{new VisitCreateDTO(new Date(1430209000000L), new Date(1430209000000L), true, 1L, 1L, Status.NEW)}
		};
	}
	
	public static Object[] provideUsersWithIncorrectDates(){
		return new Object[]{
				new Object[]{new VisitCreateDTO(new Date(1430209000000L), new Date(1430207200000L), true, 1L, 1L, Status.NEW)}
		};
	}
	
	@Test
	@Parameters(method = "provideUsersWithCorrectDates")
	public void validShouldPass(VisitCreateDTO customer) throws CustomValidationException {
		sut.validate(customer);
	}
	
	@Test(expected = CustomValidationException.class)
	@Parameters(method = "provideUsersWithIncorrectDates")
	public void validShoudFailBecouseOfInvalidDatesOrder(VisitCreateDTO customer) throws CustomValidationException{
		sut.validate(customer);
	}
	
}
