package com.insurance.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.insurance.Exception.DuplicateEntryException;
import com.insurance.Exception.ResourceNotFoundException;
import com.insurance.model.CustomerSignupDetails;
import com.insurance.repository.CustomerSignupDetailsRepository;

@Service
public class CustomerSignupService 
{
	
	@Autowired
	CustomerSignupDetailsRepository customerSignupRepository;
	
	public ResponseEntity<List<CustomerSignupDetails>> getAllCustomers()
	{
		List<CustomerSignupDetails> data = customerSignupRepository.findAll();
		
		if(data.isEmpty())
		{
			throw new ResourceNotFoundException("Data was empty");
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(data);
		}
	}
	public ResponseEntity<List<CustomerSignupDetails>> getCustomerById(String customerId) {
		List<CustomerSignupDetails> data = customerSignupRepository.findByCustomerId(customerId);
        
        if(data.isEmpty())
		{
			throw new ResourceNotFoundException("Data was empty");
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(data);
		}
    }
	
	public ResponseEntity<String> createCustomer(CustomerSignupDetails customer)
	{
		
		LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateString = date.format(formattedDate);
        // String otp = formattedDateString.substring(formattedDateString.length() - 4);
        customer.setCustomerId(formattedDateString);
        
        String mobileNo = customer.getMobileno();
       String email = customer.getEmail();
        
        Optional<CustomerSignupDetails> data = customerSignupRepository.findByMobilenoAndEmail(mobileNo,email);
        
        if(data.isEmpty())
        {
        	customerSignupRepository.save(customer);
        	return ResponseEntity.status(HttpStatus.CREATED).body("Data was inserted successfully");
        }
        else
        {
        	throw new DuplicateEntryException("Provided data was already present");
        }
        
		
	}
	
	 public ResponseEntity<List<CustomerSignupDetails>> getCustomerIdByMobileNo(String mobileno) 
	 {
		 
		 List<CustomerSignupDetails> data = customerSignupRepository.findByMobileno(mobileno);
		 if(data.isEmpty())
			{
				throw new ResourceNotFoundException("Data was empty");
			}
			else {
				return ResponseEntity.status(HttpStatus.OK).body(data);
			}
	 }
	 public ResponseEntity<String> checkMobileNumber(String mobileNumber) 
		{
			List<CustomerSignupDetails> customers = customerSignupRepository.findByMobileno(mobileNumber);
		    if (!customers.isEmpty()) {
		        return ResponseEntity.ok("Mobile number exists");
		    } else {
		        return ResponseEntity.ok("Mobile number is not exists");
		    }
		}

		public ResponseEntity<String> checkEmail(String email) {
			List<CustomerSignupDetails> customers = customerSignupRepository.findByEmail(email);
		    if (!customers.isEmpty()) {
		        return ResponseEntity.ok("Email is exists");
		    } else {
		        return ResponseEntity.ok("Email is not exists");
		    }
		}
		

	    public String deleteById(long id) {
	    	
	        if (customerSignupRepository.existsById(id)) {
	            customerSignupRepository.deleteById(id);
	            return "Deleted";
	        } else {
	            return "Not Found";
	        }
	    }
	    
	    
//	    public ResponseEntity<String> updateCustomerById(long id, CustomerSignupDetails customer) {
//	        Optional<CustomerSignupDetails> existingCustomerOptional = customerSignupRepository.findById(id);
//	        if (existingCustomerOptional.isPresent()) {
//	        	CustomerSignupDetails existingCustomer = existingCustomerOptional.get();
//	            
//	            existingCustomer.setName(customer.getName());
//	            existingCustomer.setMobileno(customer.getMobileno());
//	            existingCustomer.setEmail(customer.getEmail());
////	            existingCustomer.setPassword(customer.getPassword());
//	            
//	            
//	            String mobileNo = customer.getMobileno();
//	           String email = customer.getEmail();
//	           
//	           if(!mobileNo.equals(existingCustomer.getMobileno()))
//	           {
//	            
//	            Optional<CustomerSignupDetails> data = customerSignupRepository.findByMobilenoAndEmail(mobileNo,email);
//	            
//	            if(data.isEmpty())
//	            {
//	            	customerSignupRepository.save(existingCustomer);
//	            }
//	            else
//	            {
//	            	throw new DuplicateEntryException("Provided data was already present");
//	            }
//	            
//	            return ResponseEntity.status(HttpStatus.OK).body("Data was Updated successfully");
//	           }
//	            
//	        } else {
//	        	throw new ResourceNotFoundException("Id was not Found");
//	        }
//	    }
	    
	    
	    public ResponseEntity<String> updateCustomerById(long id, CustomerSignupDetails customer) {
	        Optional<CustomerSignupDetails> existingCustomerOptional = customerSignupRepository.findById(id);
	        
	        if (existingCustomerOptional.isPresent()) {
	            CustomerSignupDetails existingCustomer = existingCustomerOptional.get();
	            
	            // Check if mobile number is being updated
	            String newMobileNo = customer.getMobileno();
	            String newEmail = customer.getEmail();
	            
	            boolean isMobileChanged = !newMobileNo.equals(existingCustomer.getMobileno());
	            boolean isEmailChanged = !newEmail.equals(existingCustomer.getEmail());

	            if (isMobileChanged || isEmailChanged) {
	                // If mobile number or email is changed, check if the new mobile number or email already exists
	                Optional<CustomerSignupDetails> existingData = customerSignupRepository.findByMobilenoAndEmail(newMobileNo, newEmail);

	                if (existingData.isPresent()) {
	                    throw new DuplicateEntryException("Provided mobile number or email is already in use");
	                }
	            }

	            // Update customer details
	            existingCustomer.setName(customer.getName());
	            existingCustomer.setMobileno(newMobileNo);
	            existingCustomer.setEmail(newEmail);
	            // Uncomment the following line if you want to update the password as well
	            // existingCustomer.setPassword(customer.getPassword());

	            customerSignupRepository.save(existingCustomer);
	            return ResponseEntity.status(HttpStatus.OK).body("Data was updated successfully");
	        } else {
	            throw new ResourceNotFoundException("Customer with ID " + id + " was not found");
	        }
	    }

	    
	    public ResponseEntity<String> updateCustomerByMobileNo(Long id, String mobileno) {
	    	Optional<CustomerSignupDetails> existingCustomerOptional = customerSignupRepository.findById(id);
	    	
	        if (existingCustomerOptional.isPresent()) {
	        	CustomerSignupDetails existingCustomer = existingCustomerOptional.get();
	           
	        	List<CustomerSignupDetails> data = customerSignupRepository.findByMobileno(mobileno);
	            
	            if(data.isEmpty())
	            {
	            	existingCustomer.setMobileno(mobileno);

	 	           customerSignupRepository.save(existingCustomer);
	            	return ResponseEntity.status(HttpStatus.OK).body("Data was Updated successfully");
	            }
	            else
	            {
	            	throw new DuplicateEntryException("Provided data was already present");
	            }

	        } else {
	            throw new ResourceNotFoundException("Id was not Found");
	        }

	      
		   
		}
	    
	    public ResponseEntity<String> updateCustomerByEmailId( long id, String emailId) {
	    	Optional<CustomerSignupDetails> existingCustomerOptional = customerSignupRepository.findById(id);
	        if (existingCustomerOptional.isPresent()) {
	        	CustomerSignupDetails existingCustomer = existingCustomerOptional.get();
	        	
	        	List<CustomerSignupDetails> data = customerSignupRepository.findByEmail(emailId);
	            
	            if(data.isEmpty())
	            {
	            	existingCustomer.setEmail(emailId);
	        	    
	 	           customerSignupRepository.save(existingCustomer);
	 	           
	            	return ResponseEntity.status(HttpStatus.OK).body("Data was Updated successfully");
	            }
	            else
	            {
	            	throw new DuplicateEntryException("Provided data was already present");
	            }
	         
	        } else {
	        	throw new ResourceNotFoundException("Id was not Found");
	        }
		}
}