package com.pm.company.validator.field;

import com.pm.company.exception.CustomValidationException;

public interface FieldValidator<E> {
	
	void validate(E validatedObject) throws CustomValidationException;
	
}
