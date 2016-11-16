package com.pm.company.validator.field;

import java.util.ArrayList;
import java.util.List;

import com.pm.company.model.Person;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pm.company.exception.CustomValidationException;
import com.pm.company.exception.CustomValidationException.FieldError;
import com.pm.company.validator.helper.FieldValidationHelper;

@Component
public class PersonFieldValidator implements FieldValidator<Person>{

	public final static Logger logger = Logger.getLogger(PersonFieldValidator.class);

	@Autowired
	private FieldValidationHelper validationHelper;
	
	public void validate(Person customer) throws CustomValidationException{
		List<FieldError> fieldErrors = new ArrayList<>();
		validateFirstName(customer.getFirstName(), fieldErrors);
		validateLastName(customer.getLastName(), fieldErrors);
		if(fieldErrors.size() > 0){
			throw new CustomValidationException(fieldErrors);
		}
	}
	
	private void validateFirstName(String firstName, List<FieldError> fieldErrors) {
		if(!validationHelper.isTextOnly(firstName)){
			fieldErrors.add(new FieldError("firstName", "First name must be a text only"));
			logger.info("validateFirstName error");
		}
	}
	
	private void validateLastName(String lastName, List<FieldError> fieldErrors){
		if(!validationHelper.isTextOnly(lastName)){
			fieldErrors.add(new FieldError("lastName", "Last name must be a text only"));
			logger.info("validateLastName error");
		}
	}
	
}
