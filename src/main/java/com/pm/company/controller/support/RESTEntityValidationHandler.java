package com.pm.company.controller.support;

import java.util.List;
import java.util.Locale;

import com.pm.company.exception.BusinessValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.pm.company.dto.ValidationErrorDTO;
import com.pm.company.exception.CustomValidationException;
import com.pm.company.exception.ErrorInformation;
import com.pm.company.exception.ItemNotFoundException;

@ControllerAdvice
public class RESTEntityValidationHandler {
	
	private MessageSource messageSource;
	
	@Autowired
	public RESTEntityValidationHandler(MessageSource messageSource) {
		super();
		this.messageSource = messageSource;
	}

	@ExceptionHandler(CustomValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorDTO ruleForCustomValidationFailure(CustomValidationException cve){
		ValidationErrorDTO dto = new ValidationErrorDTO();
		for(CustomValidationException.FieldError fe:cve.getFieldErrors()){
			dto.addFieldError(fe.getField(), fe.getMessage());
		}
		return dto;
	}
	
	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<ErrorInformation> rulesForItemNotFound(Exception e) {
		ErrorInformation error = new ErrorInformation(e.getMessage());
		return new ResponseEntity<ErrorInformation>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BusinessValidationException.class)
	public ResponseEntity<ErrorInformation> rulesForBusinessValidation(Exception e) {
		ErrorInformation error = new ErrorInformation(e.getMessage());
		return new ResponseEntity<ErrorInformation>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorDTO ruleForValidationFailure(MethodArgumentNotValidException manve) {
		BindingResult bresult = manve.getBindingResult();
		List<FieldError> fieldErrors = bresult.getFieldErrors();

		return processFieldErrors(fieldErrors);

	}

	private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
		ValidationErrorDTO dto = new ValidationErrorDTO();

		for (FieldError fieldError : fieldErrors) {
			String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
			dto.addFieldError(fieldError.getField(), localizedErrorMessage);
		}

		return dto;
	}
	
	private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale =  LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);
        return localizedErrorMessage;
    }
}
