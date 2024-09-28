package com.ramanasoft.www.mode;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Table
@Data
public class Payment 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotEmpty( message = "Payment id can not be a null or empty")
	private String paymentid;
	
	@NotEmpty( message = "Customer id can not be a null or empty")
	private String customerid;
	
	@NotEmpty( message = "Vehicle Number can not be a null or empty")
	@Pattern(regexp = "^[A-Z]{2}\\d{2}[A-Z]{2}\\d{4}$" ,message = " Vehicle Number should be in the pattern of 'TS29AA2000'  ")
	private String vnumber;
	
	@NotEmpty( message = "Vehicle Price can not be a null or empty")
	@Pattern(regexp = "^[1-9]\\d{5}$" ,message = " Vehicle price should not be zero ")
	private String vprice;
	
	@NotEmpty( message = "Vehicle Name can not be a null or empty")
	@Pattern(regexp = "^[a-zA-Z]{3,20}$" ,message = " Vehicle Name must be 3 or more charecters ex : 'TVS'  ")
	private String vname;
	
	@NotEmpty( message = "IDV value can not be a null or empty")
	private String idv;
	
	@NotEmpty( message = "Vehicle Registration year can not be a null or empty")
	@Pattern(regexp = "^[1-9]\\d{3}$" ,message = " Vehicle Name must be 3 or more charecters ex : 'TVS'  ")
	private String vyear;
	
	@NotEmpty( message = "Premium amount value can not be a null or empty")
	private String premiumAmount;
	
	@NotEmpty( message = "Start Date can not be a null or empty")
	private LocalDate startdate;
	
	@NotEmpty( message = "End Date can not be a null or empty")
	private LocalDate enddate;
}
