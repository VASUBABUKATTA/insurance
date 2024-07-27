package com.insurance.www.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.insurance.www.model.CustomerSignup;
import com.insurance.www.model.NotificationForEmail;
import com.insurance.www.model.NotificationForMobile;
import com.insurance.www.repository.CustomerSignupRepository;
import com.insurance.www.repository.NotificationForEmailRepository;
import com.insurance.www.repository.NotificationForMobileRepository;

@Service
public class NotificationControllService 
{
	@Autowired
	NotificationForEmailRepository notificationForEmailRepository;
	
	@Autowired
	NotificationForMobileRepository notificationForMobileRepository;
	
	@Autowired
	CustomerSignupRepository cutCustomerSignupRepository;
	
	
	public List<NotificationForMobile> getAllNotificationsFroMobile ()
	{
		return notificationForMobileRepository.findAll();
	}
	
//	public Optional<NotificationForMobile> getNotificationsFroMobile (String customerId)
//	{
//		return notificationForMobileRepository.findByCustomerId(customerId);
//	}
//	
	public String sendNotificationforMobile( NotificationForMobile notificationForMobile)
	{
		notificationForMobileRepository.save(notificationForMobile);
		return "Details Are Stored";
	}
	
	public List<NotificationForMobile> getByStatusForMobile (String status)
	{
		return notificationForMobileRepository.findByStatus(status);
	}
	
	public String updateChangeStatus( String customerId ,  NotificationForMobile data )
	{
		Optional<NotificationForMobile> notificationData = notificationForMobileRepository.findByCustomerId(customerId);
		if(notificationData.isPresent())
		{
			System.out.println(data.getStatus());
			String value = data.getStatus();
			
			if(value.equals("approved"))
			{
				
			List<CustomerSignup> getExistingSignupDetails = cutCustomerSignupRepository.findByCustomerId(customerId);
			CustomerSignup signupDetailsData = getExistingSignupDetails.get(0);
			
		// Long mobileno = data.getMobileNo();
			signupDetailsData.setMobileno(data.getMobileNo());
			System.out.println("notificationData"+data.getMobileNo());
			cutCustomerSignupRepository.save(signupDetailsData);
			
			}
			if(value.equals("rejected") || value.equals("approved"))
			{
			NotificationForMobile existingOptional = notificationData.get();
			
			existingOptional.setStatus(data.getStatus());
			
			notificationForMobileRepository.delete(existingOptional);
			}
			
			return "Details are updated"+data.getStatus();
		}
		else 
		{
			return "Details are not updated";
		}
		
	}
	
	
	public String sendNotificationForEmail(NotificationForEmail notificationForEmail)
	{
		notificationForEmailRepository.save(notificationForEmail);
		return "Details Are Stored";
	}
	
	public List<NotificationForEmail> getAllNotificationsForEmail ()
	{
		return notificationForEmailRepository.findAll();
	}
	
	public List<NotificationForEmail> getByStatusForEmail (String status)
	{
		return notificationForEmailRepository.findByStatus(status);
	}
	
	public String updateChangeStatusForEmail(String customerId , NotificationForEmail data )
	{
		Optional<NotificationForEmail> notificationData = notificationForEmailRepository.findByCustomerId(customerId);
		if(notificationData.isPresent())
		{
			String value = data.getStatus();
			if(value.equals("approved"))
			{
				List<CustomerSignup> getExistingSignupDetails = cutCustomerSignupRepository.findByCustomerId(customerId);
				CustomerSignup signupDetailsData = getExistingSignupDetails.get(0);
				
				signupDetailsData.setEmail(data.getEmail());
				cutCustomerSignupRepository.save(signupDetailsData);
			}
			
			if(value.equals("rejected") || value.equals("approved"))
			{
			NotificationForEmail existingOptional = notificationData.get();
			
			existingOptional.setStatus(data.getStatus());
			
			notificationForEmailRepository.delete(existingOptional);
			}
			
			return "Details are updated"+data.getStatus();
		}
		else 
		{
			return "Details are not updated";
		}
		
	}

	public Optional<NotificationForMobile> getAllNotificationsFroMobileByCustomerId(String customerId) {
		return notificationForMobileRepository.findByCustomerId(customerId);
	}
	


	public Optional<NotificationForEmail> getAllNotificationsForEmailByCustomerId(String customerId) {
		return notificationForEmailRepository.findByCustomerId(customerId);
	
	}
	
}
