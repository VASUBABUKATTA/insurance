package com.ramanasoft.www.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramanasoft.www.mode.Customer;
import com.ramanasoft.www.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/customer")
@Tag(
		name = "CRUD REST API's for Customer Controller",
		description = "CRUD rest api for Customer to CREATE, UPDATE, FETCH and DELETE insurance details."
		)
public class CustomerController 
{
	@Autowired
	CustomerService customerService;
	
	
	
	@Operation(
			summary = "REST api to create Customer",
			description = "Creating the Customer details related to Customer."
			)
	@PostMapping("/add")
	public ResponseEntity<String> addData(@Valid @RequestBody Customer cust)
	{
		return customerService.addData(cust);
	}
	
	
	
	@Operation(
			summary = "REST api to get Customer Details",
			description = "This api is used for getting all the Customer data."
			)
	@GetMapping("/fetch")
	public ResponseEntity<List<Customer>> fetchAll()
	{
		return customerService.fetchAll();
	}
	
	
	
	@Operation(
			summary = "REST api to get Customer Details",
			description = "This api is used to get Customer data based on customer id."
			)
	@GetMapping("/getcid/{cid}")
	public ResponseEntity<Customer> getcid(@PathVariable String cid)
	{
		return customerService.getcid(cid);
	}
	
	
	
	@Operation(
			summary = "REST api to get Customer Details",
			description = "This api is used to get Customer data based on Mobile Number."
			)
	@GetMapping("/get/{mob}")
	public ResponseEntity<Customer> get(@Pattern(regexp = "(^[6-9][0-9]{9}$)", message = "mobileNumber must be start with 6 to 9 and have 10 digits")@PathVariable String mob)
	{
		return customerService.get(mob);
	}
	
	
	
	@Operation(
			summary = "REST api to get Customer Details",
			description = "This api is used to get Customer data based on Email Id."
			)
	@GetMapping("/getByEmail/{email}")
	public ResponseEntity<Customer> getEmail(@Pattern(regexp = "(^[a-z0-9._%+-]+@gmail\\.com$)", message = "Email cannot be null and must follow pattern")@PathVariable String email)
	{
		return customerService.getEmail(email);
	}
	
	
	
	@Operation(
			summary = "REST api to update Customer",
			description = "This api is used to update the mobile number for a perticual customer based on customer id."
			)
	@PutMapping("/updatemobile/{cid}/{mobile}")
	public ResponseEntity<String> updateMobile(@PathVariable String cid,
			@Pattern(regexp = "(^[6-9][0-9]{9}$)", message = "mobileNumber must be start with 6 to 9 and have 10 digits") @PathVariable String mobile)
	{
		return customerService.updateMobile(cid,mobile);
	}
	
	
	
	@Operation(
			summary = "REST api to update Customer",
			description = "This api is used to update the email id for a perticular customer based on customer id."
			)
	@PutMapping("/updateemail/{cid}/{email}")
	public ResponseEntity<String> updateEmail(@PathVariable String cid,
			@Pattern(regexp = "(^[a-z0-9._%+-]+@gmail\\.com$)", message = "Email cannot be null and must follow pattern") @Valid @PathVariable String email)
	{
		return customerService.updateEmail(cid,email);
	}
}
