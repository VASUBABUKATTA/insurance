package com.ramanasoft.www.mode;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Table
@Data
public class Customer 
{
	@Id
//	@GeneratedValue(generator = "uuid2")
//	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@NotEmpty( message = "Customer id can not be a null or empty")
    private String customerid;
	
	@NotEmpty( message = "Customer Name can not be a null or empty")
	@Pattern(regexp = "^[a-zA-Z\s]{3,60}$" ,message = " Customer Name must be 3 or more charecters ex : 'Vasu'  ")
	private String fullName;
	
	@NotEmpty( message = "Customer address can not be a null or empty")
	@Pattern(regexp = "^[a-zA-Z0-9\\s,./'-]{3,60}$" ,message = " Customer Address must be 3 or more charecters ex : 'rpt 4-3-7'  ")
	private String address;
	
	@NotEmpty( message = "Customer state can not be a null or empty")
	private String state;
	
	@NotEmpty( message = "Customer mobile number can not be a null or empty")
	@Pattern(regexp = "^[6789]\\d{9}$" ,message = " Customer mobile number must start with 6,7,8 or 9 and must contain 10 digits")
	private String mobile;
	
	@NotEmpty( message = "Customer email id can not be a null or empty")
	@Pattern(regexp = "^[a-z0-9._%+-]+@gmail\\.com$" ,message = " please provide valid email ")
	private String email;
}
