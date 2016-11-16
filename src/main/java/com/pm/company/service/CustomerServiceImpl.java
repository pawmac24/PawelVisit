package com.pm.company.service;

import java.util.ArrayList;
import java.util.List;

import com.pm.company.dto.primarykey.CustomerPrimaryKeyDTO;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.company.dto.CustomerCreateDTO;
import com.pm.company.dto.CustomerUpdateDTO;
import com.pm.company.dto.result.CustomerResultDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.model.Customer;
import com.pm.company.repository.CustomerRepository;
import com.pm.company.validator.business.BusinessValidator;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	public final static Logger logger = Logger.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private BusinessValidator<Customer, Long> customerValidator;

	@Autowired
	private Mapper mapper;

	@Override
	public CustomerPrimaryKeyDTO create(CustomerCreateDTO customerDTO) throws BusinessValidationException {
		Customer customer = mapper.map(customerDTO, Customer.class);
		customerValidator.validateCreate(customer);

		customer = customerRepository.save(customer);
		logger.info("create " + customer);
		CustomerPrimaryKeyDTO customerPrimaryKeyDTO = new CustomerPrimaryKeyDTO(customer.getCustomerId());
		return customerPrimaryKeyDTO;
	}

	@Override
	public void update(Long customerId, CustomerUpdateDTO customerDTO) throws BusinessValidationException {
		Customer customer = getCustomerById(customerId);
		mapper.map(customerDTO, customer);
		customerValidator.validateUpdate(customer);
		
		customer = customerRepository.save(customer);
		logger.info("update " + customer);
	}

	@Override
	public CustomerResultDTO find(Long customerId) throws BusinessValidationException{
		Customer customer = getCustomerById(customerId);
		CustomerResultDTO customerResultDTO = mapper.map(customer, CustomerResultDTO.class);
		return customerResultDTO;

	}

	@Override
	public void delete(Long customerId) throws BusinessValidationException {
		customerValidator.ensureThatExists(customerId);
		customerRepository.delete(customerId);
	}

	@Override
	public List<CustomerResultDTO> findAll() {
		List<Customer> customerList = customerRepository.findAll();
		List<CustomerResultDTO> customerResultDTOList = new ArrayList<>();

		for (Customer customer : customerList) {
			customerResultDTOList.add(mapper.map(customer, CustomerResultDTO.class));
		}
		return customerResultDTOList;
	}

	@Override
	public boolean exists(Long customerId) {
		return customerRepository.exists(customerId);
	}

	@Override
	public boolean peselAlreadyInUse(String pesel) {
		return customerRepository.countByPesel(pesel) > 0L;
	}

	@Override
	public boolean peselAlreadyInUseInOther(String pesel, Long customerId) {
		return customerRepository.countByPeselAndCustomerIdNot(pesel, customerId) > 0L;
	}

	private Customer getCustomerById(Long customerId) throws BusinessValidationException {
		Customer customer = customerRepository.findOne(customerId);
		if(customer == null){
			throw new BusinessValidationException("Customer with id: " + customerId + " does not exist");
		}
		return customer;
	}

}
