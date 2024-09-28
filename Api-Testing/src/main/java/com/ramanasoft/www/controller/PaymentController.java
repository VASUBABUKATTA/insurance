package com.ramanasoft.www.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.ramanasoft.www.mode.Payment;
import com.ramanasoft.www.service.InvoiceService;
import com.ramanasoft.www.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/payment")
@Tag(
		name = "CRUD REST API's for Payment Controller",
		description = "CRUD rest api for Payment to CREATE, UPDATE, FETCH and DELETE insurance details."
		)
public class PaymentController 
{
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	InvoiceService invoiceService;
	
	
	
	@Operation(
			summary = "REST api to create Payment",
			description = "Creating the Payment details related to Payment."
			)
	@PostMapping("/add")
	public ResponseEntity<String> addData(@Valid @RequestBody Payment profile)
	{
		return paymentService.addData(profile);
	}
	
	
	
	@Operation(
			summary = "REST api to get Payment Details",
			description = "This api is used to get all the payment details."
			)
	@GetMapping("/fetch")
	public ResponseEntity<List<Payment>> fetchAll()
	{
		return paymentService.fetchAll();
	}
	
	
	
	@Operation(
			summary = "REST api to get Payment Details",
			description = "This api is used to get the payment details based on cutomer id."
			)
	@GetMapping("/get/{Paymentid}")
	public ResponseEntity<List<Payment>> fetch(@PathVariable String Paymentid)
	{
		return paymentService.fetch(Paymentid);
	}
	
	
	
	@Operation(
			summary = "REST api to get Payment Details",
			description = "This api is used to get all the payment details based on vehicle number."
			)
	@GetMapping("/fetchPolocyByVnumber/{vnumber}")
	public ResponseEntity<List<Payment>> fetchPolocy(
			@PathVariable 
			@Pattern(regexp = "^[A-Z]{2}\\d{2}[A-Z]{2}\\d{4}$" ,message = " Vehicle Number should be in the pattern of 'TS29AA2000'  ")
			String vnumber)
	{
		return paymentService.fetchPolocy(vnumber);
	}
	
	
	
//	@Operation(
//			summary = "REST api to get Payment Details",
//			description = "This api is used to get all the payment details based on Phone number."
//			)
//	@GetMapping("/fetchPolocyByPnumber/{number}")
//	public List<Payment> fetchPolocyByNumber(@PathVariable String number)
//	{
//		return paymentService.fetchPolocyByNumber(number);
//	}
	
	
	
//	@Operation(
//			summary = "REST api to get Payment Details",
//			description = "This api is used to get all the payment details based on vehicle number."
//			)
//	@GetMapping("/isReniwal/{vnumber}")
//	public List<Payment> renewal(@PathVariable String vnumber)
//	{
//		return paymentService.renwal(vnumber);
//	}
	
	
	
//	@Operation(
//			summary = "REST api to Download Invoice",
//			description = "This api is used download the invoice based on paymentid and customer id."
//			)
//	@GetMapping("/create")
//    public void createPdf(HttpServletResponse response, @RequestParam String paymentid, @RequestParam String customerid) {
//        // Setting content type and response headers
//        response.setContentType("application/pdf");
//        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=RSVIInvoice.pdf");
//
//        try {
//        	invoiceService.export(response, paymentid, customerid);
//        } catch (DocumentException | IOException e) {
//            e.printStackTrace();
//        }
//    }
}