package com.ramanasoft.www.service;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ramanasoft.www.exception.DuplicateEntryException;
import com.ramanasoft.www.exception.ResourceNotFoundException;
import com.ramanasoft.www.mode.Customer;
import com.ramanasoft.www.mode.Payment;
import com.ramanasoft.www.repository.CustomerRepository;
import com.ramanasoft.www.repository.PaymentRepository;



@Service
public class PaymentService 
{
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	public ResponseEntity<String> addData(Payment profile)
	{
		profile.setStartdate(LocalDate.now());
		profile.setEnddate(profile.getStartdate().plusYears(1).minusDays(1));
		
		Payment data = paymentRepository.findByPaymentid(profile.getPaymentid());
		if(data!=null)
		{
			throw new DuplicateEntryException("Duplicates are not allowed");
		}
		
		paymentRepository.save(profile);
		
		return ResponseEntity.status(HttpStatus.OK).body("successfully done");
	}
	
	public ResponseEntity<List<Payment>> fetchAll()
	{
		List<Payment> data = paymentRepository.findAll();
		if(data.isEmpty())
		{
			throw new ResourceNotFoundException("Data not exists");
		}
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
	
	public ResponseEntity<List<Payment>> fetch(String customerid)
	{
		List<Payment> data = paymentRepository.findByCustomerid(customerid);
		if(data.isEmpty())
		{
			throw new ResourceNotFoundException("Data not exist");
		}
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	public ResponseEntity<List<Payment>> fetchPolocy(String vnumber) {
		
		List<Payment> data = paymentRepository.findByVnumber(vnumber);
		if(data.isEmpty())
		{
			throw new ResourceNotFoundException("Data not exist");
		}
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

//	public List<Payment> fetchPolocyByNumber(String number) {
//		// TODO Auto-generated method stub
//		Customer cust = customerRepository.findByMobile(number);
//		
//		return paymentRepository.findByCustomerid(cust.getCustomerid());
//	}
	
//	public List<Payment> renwal(String vnumber) {
//		
//		List<Payment> polocyDetails = paymentRepository.findByVnumber(vnumber);
//		
//		return polocyDetails;
//	}

	
}

