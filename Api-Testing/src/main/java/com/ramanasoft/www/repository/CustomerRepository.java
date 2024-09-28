package com.ramanasoft.www.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ramanasoft.www.mode.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>
{

	Customer findByMobile(String mobile);
	
	//Customer findByCustomerid(long customerid);

	Customer findByCustomerid(String customerid);

	Customer findByEmail(String email);

	Optional<Customer> findByMobileOrEmail(String mobileno, String email);
	
	List<Customer> findByEmailOrMobile(String mobileno, String email);
}
