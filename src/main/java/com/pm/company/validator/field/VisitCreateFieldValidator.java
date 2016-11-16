package com.pm.company.validator.field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pm.company.dto.VisitCreateDTO;
import com.pm.company.exception.CustomValidationException;
import com.pm.company.exception.CustomValidationException.FieldError;

@Component
public class VisitCreateFieldValidator implements FieldValidator<VisitCreateDTO>{
	
	public void validate(VisitCreateDTO visit) throws CustomValidationException {
		List<FieldError> fieldErrors = new ArrayList<>();
		validateDateOrder(visit.getStart(), visit.getEnd(), fieldErrors);
		if (fieldErrors.size() > 0) {
			throw new CustomValidationException(fieldErrors);
		}
	}
	
	public void validateDateOrder(Date start, Date stop, List<FieldError> fieldErrors) {
		if (start.after(stop)) {
			fieldErrors.add(new FieldError("start", "Begining of a period must be after its ending"));
		}
	}
}
