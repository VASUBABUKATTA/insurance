package com.insurance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.insurance.Exception.DuplicateEntryException;
import com.insurance.Exception.ResourceNotFoundException;
import com.insurance.model.StructureAndDetails;
import com.insurance.repository.StructureDetailsRepository;

@Service
public class StructureAndDetailsService 
{
	
	@Autowired
	StructureDetailsRepository propertyInsuranceRepository;
	
	public ResponseEntity<List<StructureAndDetails>> getAllDetails()
	{
		List<StructureAndDetails> data = propertyInsuranceRepository.findAll();
		
		if(data.isEmpty())
		{
			throw new ResourceNotFoundException("Data was not Found");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
	public ResponseEntity<List<StructureAndDetails>> getStructureDetailsByCustomerId(String customerId) {
        List<StructureAndDetails> structuresOptional = propertyInsuranceRepository.findByCustomerId(customerId);

	    if (structuresOptional.isEmpty()) {
	    	throw new ResourceNotFoundException("Customer Id was not Found ");
	    }else {

	    	 return ResponseEntity.status(HttpStatus.OK).body(structuresOptional);
	    }
    }
	
	public ResponseEntity<String> createDetails( StructureAndDetails details )
	{
		String id = details.getPaymentId();
		String id1 = details.getCustomerId();
		
		Optional<StructureAndDetails> data = propertyInsuranceRepository.findByCustomerIdAndPaymentId(id1,id);
		
		if(data.isEmpty())
		{
			propertyInsuranceRepository.save(details);
			
			return ResponseEntity.status(HttpStatus.CREATED).body("Structure Details are inserted SuccessFully");
			
		}else {
			throw new DuplicateEntryException(" Provided Data was already existed");
		}
		
		
	}	
	
	public ResponseEntity<Optional<StructureAndDetails>> getStructureDetailsById(long id) {
		Optional<StructureAndDetails> structireTableDetails = propertyInsuranceRepository.findById(id);
		if (structireTableDetails.isEmpty()) {
			   throw new ResourceNotFoundException("Id Was Not Found");
		   }
	   return ResponseEntity.status(HttpStatus.OK).body(structireTableDetails);
    }
	
	
	 public ResponseEntity<String> deleteStructureDetailsById(long id) {
		Optional<StructureAndDetails> existingDetailsOptional = propertyInsuranceRepository.findById(id);
		if (existingDetailsOptional.isPresent()) {
			propertyInsuranceRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Structure details deleted successfully");
		} else {
			throw new ResourceNotFoundException("Id Was Not Found");
		}
	    }
	 
	 public ResponseEntity<String> updateStructureDetailsById(long id, StructureAndDetails details) {
	        Optional<StructureAndDetails> existingDetailsOptional = propertyInsuranceRepository.findById(id);
	        
	        
	        if (existingDetailsOptional.isPresent()) {
	            StructureAndDetails existingDetails = existingDetailsOptional.get();
	           
	            existingDetails.setMarketValue(details.getMarketValue());
	            existingDetails.setSquareFeet(details.getSquareFeet());
	            existingDetails.setPincode(details.getPincode());
	            existingDetails.setBuildingAge(details.getBuildingAge());
	            existingDetails.setSecurity(details.getSecurity());
	            existingDetails.setEffected(details.getEffected());
	            existingDetails.setPerson(details.getPerson());
	            
	           propertyInsuranceRepository.save(existingDetails);
	           return ResponseEntity.status(HttpStatus.OK).body("Updated SuccessFully");
	        } else {
	        	throw new ResourceNotFoundException("Id Was Not Found");
	        }
	    }


}