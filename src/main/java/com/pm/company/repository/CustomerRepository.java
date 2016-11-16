package com.pm.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pm.company.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	Long countByPesel(String pesel);

	Long countByPeselAndCustomerIdNot(String pesel, Long customerId);
	
}
