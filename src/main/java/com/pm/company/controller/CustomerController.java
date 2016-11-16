package com.pm.company.controller;

import java.util.List;

import javax.validation.Valid;

import com.pm.company.dto.primarykey.CustomerPrimaryKeyDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.exception.CustomValidationException;
import com.pm.company.model.Person;
import com.pm.company.validator.field.FieldValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pm.company.dto.CustomerCreateDTO;
import com.pm.company.dto.CustomerUpdateDTO;
import com.pm.company.dto.result.CustomerResultDTO;
import com.pm.company.service.CustomerService;

@RestController
@RequestMapping(value = "customers")
public class CustomerController {

	public final static Logger logger = Logger.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private FieldValidator<CustomerCreateDTO> customerCrtValidator;
	
	@Autowired
	private FieldValidator<Person> customerUpdtValidator;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<CustomerResultDTO> findAllEmployees() {
		List<CustomerResultDTO> customerResultDTOList = customerService.findAll();
		logger.info("Find " + customerResultDTOList);
		return customerResultDTOList;
	}

	@RequestMapping(value = "/get/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<?> findCustomerById(@PathVariable Long customerId) throws BusinessValidationException {
		CustomerResultDTO customerResultDTO = customerService.find(customerId);
		logger.info("Find " + customerResultDTO);
		return new ResponseEntity<>(customerResultDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> addCustomer(@Valid @RequestBody CustomerCreateDTO customerDTO) throws CustomValidationException,
			BusinessValidationException {
		customerCrtValidator.validate(customerDTO);
		CustomerPrimaryKeyDTO customerPKeyDTO = customerService.create(customerDTO);
		logger.info("Add customer with " + customerPKeyDTO.getId());
		return new ResponseEntity<>(customerPKeyDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/update/{customerId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCustomer(@PathVariable Long customerId, @Valid @RequestBody CustomerUpdateDTO customerDTO)
			throws CustomValidationException, BusinessValidationException {
		customerUpdtValidator.validate(customerDTO);
		customerService.update(customerId, customerDTO);
		logger.info("Update customer with id " + customerId + " was successful");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/delete/{customerId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCustomerById(@PathVariable Long customerId) throws BusinessValidationException {
		customerService.delete(customerId);
		logger.info("Delete by id = " + customerId + "successful");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
