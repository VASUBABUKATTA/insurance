package com.ramanasoft.www.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ramanasoft.www.exception.DuplicateEntryException;
import com.ramanasoft.www.exception.ResourceNotFoundException;
import com.ramanasoft.www.mode.Vehicle;
import com.ramanasoft.www.repository.VehicleRepository;

@Service
public class VehicleService 
{
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	public ResponseEntity<String> addData(Vehicle veh)
	{
		Vehicle details = vehicleRepository.findByVnumber(veh.getVnumber());
		if(details!=null)
		{
			throw new DuplicateEntryException("Duplicates are not allowed");
		}
		vehicleRepository.save(veh);
		
		return ResponseEntity.status(HttpStatus.OK).body("Details are entered successfully");
	}
	
	public ResponseEntity<List<Vehicle>> fetchAll()
	{
		List<Vehicle> data = vehicleRepository.findAll();
		
		if(data.isEmpty())
		{
			throw new ResourceNotFoundException("Data was Empty");
		}
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
	
//	public String updateData(Vehicle veh) 
//	{
//		Vehicle v = vehicleRepository.findById(veh.getId()).get();
//		v.setVname(veh.getVname());
//		v.setVnumber(veh.getVnumber());
//		v.setVyear(veh.getVyear());
//
//		
//		vehicleRepository.save(v);
//		
//		return "data saved";
//	}
	

	
	public ResponseEntity<Vehicle> getVehicle(String vehicleNumber)
	{
		Vehicle veh =  vehicleRepository.findByVnumber(vehicleNumber);
		
		if(veh==null)
		{
			throw new ResourceNotFoundException("Data was Empty");
		}
		return ResponseEntity.status(HttpStatus.OK).body(veh);
	}

	public ResponseEntity<List<Double>> caliculate(String price,int vyear)
	{
		List<Double> list = new ArrayList<Double>();
		int p = Integer.parseInt(price);
		int year = vyear;
		double claim_amount=0,premium_amount=0;
		
		if(year<=2024 && year>2021)
		{
			claim_amount=(p*60)/100.0;
			premium_amount=900;
		}
		else if(year>2019)
		{
			claim_amount=(p*55)/100.0;
			premium_amount=850;
		}
		else if(year>2015)
		{
			claim_amount=(p*50)/100.0;
			premium_amount=800;
		}
		else if(year>2013)
		{
			claim_amount=(p*45)/100.0;
			premium_amount=750;
		}
		else if(year>2010)
		{
			claim_amount=(p*40)/100.0;
			premium_amount=700;
		}
		else
		{
			claim_amount=(p*35)/100.0;
			premium_amount=650;
		}
		
		list.add(Math.ceil(claim_amount));
		list.add(premium_amount);
		
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	  
	  


}
