package com.pm.company.service;

import java.util.List;

import com.pm.company.dto.CustomerCreateDTO;
import com.pm.company.dto.CustomerUpdateDTO;
import com.pm.company.dto.primarykey.CustomerPrimaryKeyDTO;
import com.pm.company.dto.result.CustomerResultDTO;
import com.pm.company.exception.BusinessValidationException;

public interface CustomerService {
	
	CustomerPrimaryKeyDTO create(CustomerCreateDTO customerDTO) throws BusinessValidationException;
	
	void update(Long customerId, CustomerUpdateDTO customerDTO) throws BusinessValidationException;

	CustomerResultDTO find(Long customerId) throws BusinessValidationException;
	
	void delete(Long customerId) throws BusinessValidationException;
	
	List<CustomerResultDTO> findAll();

	boolean exists(Long customerId);
	
	boolean peselAlreadyInUse(String pesel);
	
	boolean peselAlreadyInUseInOther(String pesel, Long customerId);
	
}
