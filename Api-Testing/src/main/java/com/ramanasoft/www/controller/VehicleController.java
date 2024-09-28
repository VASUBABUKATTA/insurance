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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ramanasoft.www.mode.Vehicle;
import com.ramanasoft.www.service.Otps;
import com.ramanasoft.www.service.VehicleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/vehicle")
@Tag(
		name = "CRUD REST API's for Vehicle Controller",
		description = "CRUD rest api in vehicle to CREATE, UPDATE, FETCH and DELETE insurance details."
		)

public class VehicleController 
{
	
	@Autowired
	VehicleService vehicleService;
	
	@Autowired
	Otps otpService;
	
	@Operation(
			summary = "REST api to create Vehicle",
			description = "Creating the vehicle details related to vehicle."
			)
	@PostMapping("/add")
	public ResponseEntity<String> addData(@Valid @RequestBody Vehicle veh)
	{
		return vehicleService.addData(veh);
	}
	
	
	@Operation(
			summary = "REST api to fetch Vehicle Details",
			description = "Getting all the vehicle details that are available."
			)
	@GetMapping("/fetch")
	public ResponseEntity<List<Vehicle>> fetchAll()
	{
		return vehicleService.fetchAll();
	}
	
	
//	@Operation(
//			summary = "REST api to update Vehicle",
//			description = "Updating the vehicle details for Vehicle."
//			)
//	@PutMapping("/update")
//	public String updateData(@RequestBody Vehicle veh)
//	{
//		return vehicleService.updateData(veh);
//	}
	
	
	
	
	@Operation(
			summary = "REST api to get Vehicle based on vehicle number",
			description = "Getting the vehilce details for the Vehicle based on Vehicle number using pathvariable."
			)
	@GetMapping("/get/{vehicleNumber}")
	public ResponseEntity<Vehicle> getVehicle(@Pattern(regexp = "^[A-Z]{2}\\d{2}[A-Z]{2}\\d{4}$" ,message = " Vehicle Number should be in the pattern of 'TS29AA2000'  ") @PathVariable String vehicleNumber)
	{
		return vehicleService.getVehicle(vehicleNumber);
	}
	
	
	@Operation(
			summary = "REST api to get Quotation details",
			description = "Getting the Quotation for the vehicle based on price and registration year."
			)
	@GetMapping("/calculate")
	public ResponseEntity<List<Double>> calculate(
	        @RequestParam 
	        @Pattern(regexp = "^[1-9]\\d{0,5}$", message = "Price must be a positive number with up to 6 digits and not zero") String price,
	        
	        @RequestParam 
	        @Min(value = 2010, message = "Year must be between 2010 and 2025")
	        @Max(value = 2025, message = "Year must be between 2010 and 2025")
	        int vyear)
	{
		return vehicleService.caliculate(price,vyear);
	}
	
	
	
	@Operation(
			summary = "REST api to send mobileOtp",
			description = "This api is used to send the mobile otp for the given mobile number and return's otp."
			)
	@GetMapping("/sendOtp")
	public ResponseEntity<String> mobileOtp(@Pattern(regexp = "(^[6-9][0-9]{9}$)", message = "mobileNumber must be start with 6 to 9 and have 10 digits") @RequestParam String mobile) 
	{
		return otpService.mobileOtp(mobile);
	}
	
	
	
	@Operation(
			summary = "REST api to send Email for registration",
			description = "This api is used to send Email otp for registration of new user."
			)
	@PostMapping("/sendEmailOtpForRegistration/{toEmail}")
	public ResponseEntity<String> emailOtp(@Pattern(regexp = "(^[a-z0-9._%+-]+@gmail\\.com$)", message = "Email cannot be null and must follow pattern") @PathVariable String toEmail) 
	{
		return otpService.emailOtp(toEmail);
	}
	
	
	
	@Operation(
			summary = "REST api to send Email for updation",
			description = "This api is used to send Email otp for updation of mail."
			)
	@PostMapping("/sendEmailOtpForUpdation/{toEmail}")
	public ResponseEntity<String> emailOtpForUpdation(@Pattern(regexp = "(^[a-z0-9._%+-]+@gmail\\.com$)", message = "Email cannot be null and must follow pattern") @PathVariable String toEmail) 
	{
		return otpService.emailOtpForUpdation(toEmail);
	}
	

	
	@Operation(
			summary = "REST api to send Email for Quotaion",
			description = "This api is used to send Email for Quatation details of vehicle insurance."
			)
	@PostMapping("/sendEmail")
	public ResponseEntity<String> quotEmail(
			@Pattern(regexp = "(^[a-z0-9._%+-]+@gmail\\.com$)", message = "Email cannot be null and must follow pattern") 
			@RequestParam 
			String toEmail,
			
			@Pattern(regexp = "^[A-Z]{2}\\d{2}[A-Z]{2}\\d{4}$" ,message = " Vehicle Number should be in the pattern of 'TS29AA2000'  ") 
			@RequestParam 
			String vnumber,
			
			@RequestParam 
	        @Pattern(regexp = "^[1-9]\\d{0,5}$", message = "Price must be a positive number with up to 6 digits and not zero") 
			String price,
			
			@RequestParam String idv,@RequestParam String premiumAmount)
	{
		return otpService.quotEmail(toEmail,vnumber,price,idv,premiumAmount);
	}

}