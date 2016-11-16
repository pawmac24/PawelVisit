package com.pm.company.validator.field;

import com.pm.company.dto.ScheduleCreateUpdateDTO;
import com.pm.company.exception.CustomValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ScheduleCreateUpdateFieldValidator implements FieldValidator<ScheduleCreateUpdateDTO>{
	
	public void validate(ScheduleCreateUpdateDTO scheduleDTO) throws CustomValidationException {
		List<CustomValidationException.FieldError> fieldErrors = new ArrayList<>();
		validateDateOrder(scheduleDTO.getStart(), scheduleDTO.getStop(), fieldErrors);
		if(fieldErrors.size() > 0){
			throw new CustomValidationException(fieldErrors);
		}
	}
	
	public void validateDateOrder(Date start, Date stop, List<CustomValidationException.FieldError> fieldErrors) {
		if (start.after(stop)) {
			fieldErrors.add(new CustomValidationException.FieldError("start", "Beginning of a period must be after its ending"));
		}
	}
}
