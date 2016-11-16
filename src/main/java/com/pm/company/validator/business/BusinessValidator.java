package com.pm.company.validator.business;

import com.pm.company.exception.BusinessValidationException;

public interface BusinessValidator<E, ID> {

	void validateCreate(E validatedObject) throws BusinessValidationException;

	void validateUpdate(E validatedObject) throws BusinessValidationException;

	void validateDelete(ID validatedObjectId) throws BusinessValidationException;

	void ensureThatExists(Long validatedObjectId) throws BusinessValidationException;

}