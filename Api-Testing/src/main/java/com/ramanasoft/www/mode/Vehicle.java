package com.ramanasoft.www.mode;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Table
@Data
public class Vehicle 
{
	@Id
	private  Long id;
	
	
	@NotEmpty( message = "Vehicle Number cannot be null or empty")
	@Pattern(regexp = "^[A-Z]{2}\\d{2}[A-Z]{2}\\d{4}$" ,message = " Vehicle Number should be in the pattern of 'TS29AA2000'  ")
	private String vnumber;
	
	@NotEmpty( message = "Vehicle Name can not be null or empty")
	@Pattern(regexp = "^[a-zA-Z]{3,20}$" ,message = " Vehicle Name must be 3 or more charecters ex : 'TVS'  ")
	private String vname;
	
//	@NotEmpty( message = "Vehicle Registration year can not be null or empty")
	@Min(value = 2005, message = "minimum must be 2005")
    @Max(value = 2025, message = "maximum must be 2025")
	@NotNull( message = "Year can not be null or empty")
	private int vyear;
	
	
	
	
}
