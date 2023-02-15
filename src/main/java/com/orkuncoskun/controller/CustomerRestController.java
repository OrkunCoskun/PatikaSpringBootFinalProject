package com.orkuncoskun.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orkuncoskun.business.dto.CustomerDto;
import com.orkuncoskun.business.services.CustomerServices;
import com.orkuncoskun.data.entity.CustomerEntity;
import com.orkuncoskun.data.repository.CustomerRepository;

@RestController
@RequestMapping({ "/api/v1", "/" })
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerRestController {

	@Autowired
	CustomerServices customerServices;

	@Autowired
	private CustomerRepository customerRepository;

	//APPLY FOR CREDIT
	//http://localhost:8080/api/v1/apply/1
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

		// LIST
		// http://localhost:8080/api/v1/customers
		@GetMapping("/customers")
		public List<CustomerDto> getAllCustomers() {
			List<CustomerDto> customerDtoList = customerServices.getAllCustomers();
			return customerDtoList;
		}

		// FIND
		// http://localhost:8080/api/v1/customers/1
		@GetMapping("/customers/{id}")
		public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) throws Throwable {
			ResponseEntity<CustomerDto> customerDto = customerServices.getCustomerById(id);
			return customerDto;
		}

		// SAVE
		// http://localhost:8080/api/v1/customers
		@PostMapping("/customers")
		public CustomerDto createCustomer(@RequestBody CustomerDto customerDto) {
			customerServices.createCustomers(customerDto);
			return customerDto;
		}

		// UPDATE
		// http://localhost:8080/api/v1/customers/1
		@PutMapping("/customers/{id}")
		public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto)
				throws Throwable {
			customerServices.updateCustomer(id, customerDto);
			return ResponseEntity.ok(customerDto);
		}

		// DELETE
		// http://localhost:8080/api/v1/customers/1
		@DeleteMapping("/customers/{id}")
		public ResponseEntity<Map<String, Boolean>> deleteCustomer(@PathVariable Long id) throws Throwable {
			customerServices.deleteCustomer(id);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return ResponseEntity.ok(response);
		}
}
