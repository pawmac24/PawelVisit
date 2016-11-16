package com.pm.company.validator.field;

import java.util.Date;

import com.pm.company.dto.ScheduleCreateDTO;
import com.pm.company.exception.CustomValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ScheduleCreateFieldValidatorTest {
	
	private ScheduleCreateFieldValidator sut;
	

	@Before
	public void setUp(){
		sut = new ScheduleCreateFieldValidator();
	}
	
	public static Object[] provideUsersWithCorrectDates(){
		return new Object[]{
			new Object[]{new ScheduleCreateDTO(new Date(1430207200000L), new Date(1430209000000L), "Place", 1L, 1L)},
			new Object[]{new ScheduleCreateDTO(new Date(1430209000000L), new Date(1430209000000L), "Place", 1L, 1L)}
		};
	}
	
	public static Object[] provideUsersWithIncorrectDates(){
		return new Object[]{
				new Object[]{new ScheduleCreateDTO(new Date(1430209000000L), new Date(1430207200000L), "Place", 1L, 1L)}
		};
	}
	
	@Test
	@Parameters(method = "provideUsersWithCorrectDates")
	public void validShouldPass(ScheduleCreateDTO customer) throws CustomValidationException {
		sut.validate(customer);
	}
	
	@Test(expected = CustomValidationException.class)
	@Parameters(method = "provideUsersWithIncorrectDates")
	public void validShoudFailBecouseOfInvalidDatesOrder(ScheduleCreateDTO customer) throws CustomValidationException{
		sut.validate(customer);
	}
	
}
