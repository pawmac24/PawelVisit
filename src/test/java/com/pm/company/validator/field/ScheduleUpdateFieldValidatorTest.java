package com.pm.company.validator.field;

import java.util.Date;

import com.pm.company.dto.ScheduleUpdateDTO;
import com.pm.company.exception.CustomValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ScheduleUpdateFieldValidatorTest {
	
	private ScheduleUpdateFieldValidator sut;
	

	@Before
	public void setUp(){
		sut = new ScheduleUpdateFieldValidator();
	}
	
	public static Object[] provideUsersWithCorrectDates(){
		return new Object[]{
			new Object[]{new ScheduleUpdateDTO(new Date(1430207200000L), new Date(1430209000000L), "Place")},
			new Object[]{new ScheduleUpdateDTO(new Date(1430209000000L), new Date(1430209000000L), "Place")}
		};
	}
	
	public static Object[] provideUsersWithIncorrectDates(){
		return new Object[]{
				new Object[]{new ScheduleUpdateDTO(new Date(1430209000000L), new Date(1430207200000L), "Place")}
		};
	}
	
	@Test
	@Parameters(method = "provideUsersWithCorrectDates")
	public void validShouldPass(ScheduleUpdateDTO customer) throws CustomValidationException {
		sut.validate(customer);
	}
	
	@Test(expected = CustomValidationException.class)
	@Parameters(method = "provideUsersWithIncorrectDates")
	public void validShoudFailBecouseOfInvalidDatesOrder(ScheduleUpdateDTO customer) throws CustomValidationException{
		sut.validate(customer);
	}
	
}
