package com.pm.company.validator.field;

import java.util.ArrayList;
import java.util.List;

import com.pm.company.dto.CustomerCreateDTO;
import com.pm.company.exception.CustomValidationException;
import com.pm.company.validator.helper.FieldValidationHelper;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerCreateFieldValidator implements FieldValidator<CustomerCreateDTO>{
	
	@Autowired
	private FieldValidationHelper validationHelper;
	
	public void validate(CustomerCreateDTO customer) throws CustomValidationException {
		List<CustomValidationException.FieldError> fieldErrors = new ArrayList<>();
		validatePesel(customer.getPesel(), fieldErrors);
		validateFirstName(customer.getFirstName(), fieldErrors);
		validateLastName(customer.getLastName(), fieldErrors);
		if(fieldErrors.size() > 0){
			throw new CustomValidationException(fieldErrors);
		}
	}

	private void validatePesel(String pesel, List<CustomValidationException.FieldError> fieldErrors) {
		String fieldName = "pesel";
		if(!NumberUtils.isDigits(pesel)){
			fieldErrors.add(new CustomValidationException.FieldError(fieldName, "Pesel must be a number"));
		}
	}
	
	private void validateFirstName(String firstName, List<CustomValidationException.FieldError> fieldErrors) {
		if(!validationHelper.isTextOnly(firstName)){
			fieldErrors.add(new CustomValidationException.FieldError("firstName", "First name must be a text only"));
		}
	}
	
	private void validateLastName(String lastName, List<CustomValidationException.FieldError> fieldErrors){
		if(!validationHelper.isTextOnly(lastName)){
			fieldErrors.add(new CustomValidationException.FieldError("lastName", "Last name must be a text only"));
		}
	}
	
}
