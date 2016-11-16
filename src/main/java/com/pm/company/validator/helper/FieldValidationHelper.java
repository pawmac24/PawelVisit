package com.pm.company.validator.helper;

import org.springframework.stereotype.Component;

@Component
public class FieldValidationHelper {

	public boolean isTextOnly(String text) {
		return !text.matches(".*\\d+.*");
	}

	
}
