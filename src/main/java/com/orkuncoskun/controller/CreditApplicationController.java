package com.orkuncoskun.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orkuncoskun.data.entity.CustomerEntity;
import com.orkuncoskun.data.repository.CustomerRepository;

@RestController
@RequestMapping("/api/v1")
public class CreditApplicationController {

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/apply/{customerId}")
	public ResponseEntity<Map<String, Object>> applyForCredit(@PathVariable Long customerId) {
	    CustomerEntity customer = customerRepository.findById(customerId).orElse(null);
	    if (customer == null) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Customer not found with id: " + customerId);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }

	    double creditScore = customer.getCreditScore();
	    double monthlyIncome = customer.getMonthlyIncome();
	    int creditLimitMultiplier = 4;

	    Map<String, Object> response = new HashMap<>();
	    response.put("customerId", customerId);
	    response.put("phoneNumber", customer.getPhoneNumber());

	    if (creditScore < 500) {
	        response.put("status", "REJECTED");
	        response.put("message", "Credit score is below 500, credit application rejected.");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    } else if (creditScore >= 500 && creditScore < 1000) {
	        if (monthlyIncome < 5000) {
	            response.put("status", "APPROVED");
	            response.put("message", "Credit application approved and assigned limit of 10000 TL.");
	            response.put("limit", 10000);
	            return new ResponseEntity<>(response, HttpStatus.OK);
	        } else if (monthlyIncome >= 5000 && monthlyIncome < 10000) {
	            response.put("status", "APPROVED");
	            response.put("message", "Credit application approved and assigned limit of 20000 TL.");
	            response.put("limit", 20000);
	            return new ResponseEntity<>(response, HttpStatus.OK);
	        } else if (monthlyIncome >= 10000) {
	            double limit = monthlyIncome * creditLimitMultiplier / 2;
	            response.put("status", "APPROVED");
	            response.put("message", "Credit application approved and assigned limit of " + limit + " TL.");
	            response.put("limit", limit);
	            return new ResponseEntity<>(response, HttpStatus.OK);
	        }
	    } else if (creditScore >= 1000) {
	        double limit = monthlyIncome * creditLimitMultiplier;
	        response.put("status", "APPROVED");
	        response.put("message", "Credit application approved and assigned limit of " + limit + " TL.");
	        response.put("limit", limit);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    response.put("status", "INTERNAL_ERROR");
	    response.put("message", "Something went wrong.");
	    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
