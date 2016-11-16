package com.pm.company.validator.helper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class CustomValidationHelperTest {

	private FieldValidationHelper sut;
	
	@Before
	public void setUp(){
		sut = new FieldValidationHelper();
	}
	
	public static Object[] provideTextOnlyStrings(){
		return new Object[]{
				new Object[]{"Ann Mary has no digits."},
				new Object[]{"Lots of !@#%$ #@ ) ? signs, but no digit."},
				new Object[]{"Another text only string."}
		};
	}
	
	@Test
	@Parameters(method = "provideTextOnlyStrings")
	public void testTextOnlyTrue(String textOnlyString) {
		assertTrue(sut.isTextOnly(textOnlyString));
	}
	
	public static Object[] provideStringsContainingDigits(){
		return new Object[]{
				new Object[]{"123442303"},
				new Object[]{"1234423 03"},
				new Object[]{"a123442303"},
				new Object[]{"123442303cxc"},
				new Object[]{"A digit hidden 1 here."},
				new Object[]{"2000567890d"}
		};
	}
	
	@Test
	@Parameters(method = "provideStringsContainingDigits")
	public void testTextOnlyFalse(String textWithDigits){
		assertFalse(sut.isTextOnly(textWithDigits));
	}

}
