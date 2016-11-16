package com.pm.company.validator.field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pm.company.dto.VisitUpdateDTO;
import com.pm.company.exception.CustomValidationException;
import org.springframework.stereotype.Component;

@Component
public class VisitUpdateFieldValidator implements FieldValidator<VisitUpdateDTO>{
	
	public void validate(VisitUpdateDTO visit) throws CustomValidationException {
		List<CustomValidationException.FieldError> fieldErrors = new ArrayList<>();
		validateDateOrder(visit.getStart(), visit.getEnd(), fieldErrors);
		if (fieldErrors.size() > 0) {
			throw new CustomValidationException(fieldErrors);
		}
	}
	
	public void validateDateOrder(Date start, Date stop, List<CustomValidationException.FieldError> fieldErrors) {
		if (start.after(stop)) {
			fieldErrors.add(new CustomValidationException.FieldError("start", "Begining of a period must be after its ending"));
		}
	}
}
