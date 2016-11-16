package com.pm.company.validator.business;

import com.pm.company.exception.BusinessValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pm.company.model.Customer;
import com.pm.company.service.CustomerService;

@Component
public class CustomerBusinessValidator implements BusinessValidator<Customer, Long> {

	@Autowired
	private CustomerService customerSrv;

	@Override
	public void validateCreate(Customer validatedObject) throws BusinessValidationException {
		if (customerSrv.peselAlreadyInUse(validatedObject.getPesel())) {
			throw new BusinessValidationException("Pesel already in use");
		}
	}

	@Override
	public void validateUpdate(Customer validatedObject) throws BusinessValidationException {
		if (customerSrv.peselAlreadyInUseInOther(validatedObject.getPesel(),
				validatedObject.getCustomerId())) {
			throw new BusinessValidationException("Pesel already in use in other customers");
		}
	}

	@Override
	public void validateDelete(Long customerId) throws BusinessValidationException {

	}

	@Override
	public void ensureThatExists(Long validatedObjectId) throws BusinessValidationException {
		if(!customerSrv.exists(validatedObjectId)){
			throw new BusinessValidationException("Customer with id: " + validatedObjectId + " does not exist");
		}
	}

	
	
}
