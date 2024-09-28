package com.ramanasoft.www.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ramanasoft.www.exception.DuplicateEntryException;
import com.ramanasoft.www.exception.ResourceNotFoundException;
import com.ramanasoft.www.mode.Customer;
import com.ramanasoft.www.repository.CustomerRepository;

@Service
public class CustomerService 
{
	@Autowired
	CustomerRepository customerRepository;
	
	
	public ResponseEntity<String> addData(Customer cust)
	{
		LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateString = date.format(formattedDate);
        // String otp = formattedDateString.substring(formattedDateString.length() - 4);
        cust.setCustomerid("VI"+formattedDateString);
        
        String mobileno = cust.getMobile();
        String email = cust.getEmail();
        
//        Optional<Customer> customerDetails = customerRepository.findByMobileOrEmail(mobileno,email);
//        
//        if(customerDetails.isEmpty())
//        {
//        	return ResponseEntity.status(HttpStatus.OK).body(customerRepository.save(cust));
//        }
//        else 
//        {
//        	return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
//        }
        
        List<Customer> customerDetails = customerRepository.findByEmailOrMobile(email,mobileno);
        
        if(customerDetails.isEmpty())
        {
        	customerRepository.save(cust);
        	return ResponseEntity.status(HttpStatus.OK).body("Customer data insertion successful");
        }
        else 
        {
        	return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicates are not allowed");
        }
		
	}


	public ResponseEntity<List<Customer>> fetchAll() {
		
		List<Customer> customerDetails = customerRepository.findAll();
		
		if(!customerDetails.isEmpty())
        {
        	return ResponseEntity.status(HttpStatus.OK).body(customerDetails);
        }
        else 
        {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
	}
	
	
	public ResponseEntity<Customer> getcid(String cid) {
		
		 
		Customer customerDetails = customerRepository.findByCustomerid(cid);
		
		if(customerDetails==null)
        {
//			throw new ResourceNotFoundException("Data was Empty");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
//        else 
//        {
//        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
		
		return ResponseEntity.status(HttpStatus.OK).body(customerDetails);
	}


	public ResponseEntity<Customer> get(String mob) {
		
		
		Customer customerDetails = customerRepository.findByMobile(mob);
		
		if(customerDetails==null)
        {
//			throw new ResourceNotFoundException("Data was Empty");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
//        else 
//        {
//        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
		
		return ResponseEntity.status(HttpStatus.OK).body(customerDetails);
	}
	
	
	public ResponseEntity<Customer> getEmail(String email) {
		
		
		Customer customerDetails = customerRepository.findByEmail(email);
		
		if(customerDetails==null)
        {
//			throw new ResourceNotFoundException("Data was Empty");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
//        else 
//        {
//        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
		
		return ResponseEntity.status(HttpStatus.OK).body(customerDetails);
	}


	public ResponseEntity<String> updateMobile(String cid, String mobile) {
		Customer existingCustomer = customerRepository.findByCustomerid(cid);
		Customer dataCustomer = customerRepository.findByMobile(mobile);
		
		if(existingCustomer ==null)
		{
			throw new ResourceNotFoundException("Data was not present");
		}
		else if(dataCustomer!=null)
		{
			throw new DuplicateEntryException("Duplicates are not allowed");
		}
		
		existingCustomer.setMobile(mobile);
		customerRepository.save(existingCustomer);
		return ResponseEntity.status(HttpStatus.OK).body("Data was inserted successful");
	}
	
	
	public ResponseEntity<String> updateEmail(String cid, String email) {
		Customer existingCustomer = customerRepository.findByCustomerid(cid);
		Customer dataCustomer = customerRepository.findByEmail(email);
		
		if(existingCustomer ==null)
		{
			throw new ResourceNotFoundException("Data was not present");
		}
		else if(dataCustomer!=null)
		{
			throw new DuplicateEntryException("Duplicates are not allowed");
		}
		
		existingCustomer.setEmail(email);
		customerRepository.save(existingCustomer);
		return ResponseEntity.status(HttpStatus.OK).body("Data was inserted successful");
	}
}
