package com.pm.company.exception;

import java.util.ArrayList;
import java.util.List;

public class CustomValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	private List<FieldError> fieldErrors;

	public CustomValidationException() {
		super();
		fieldErrors = new ArrayList<>();
	}

	public CustomValidationException(String message) {
		super(message);
		fieldErrors = new ArrayList<>();
	}

	public CustomValidationException(List<FieldError> fieldErrors) {
		super();
		this.fieldErrors = fieldErrors;
	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public static class FieldError {
		private String field;
		private String message;

		public FieldError(String field, String message) {
			this.field = field;
			this.message = message;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}

}
