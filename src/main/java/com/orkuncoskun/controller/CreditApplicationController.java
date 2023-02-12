package com.orkuncoskun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orkuncoskun.data.entity.CustomerEntity;
import com.orkuncoskun.data.repository.CustomerRepository;

@RestController
@RequestMapping("/credit-application")
public class CreditApplicationController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/apply/{customerId}")
    public ResponseEntity<String> applyForCredit(@PathVariable Long customerId) {
        CustomerEntity customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            return new ResponseEntity<>("Customer not found with id: " + customerId, HttpStatus.NOT_FOUND);
        }

        double creditScore = customer.getCreditScore();
        double monthlyIncome = customer.getMonthlyIncome();
        int creditLimitMultiplier = 4;

        if (creditScore < 500) {
            return new ResponseEntity<>("Credit score is below 500, credit application rejected for customer with id: " + customerId, HttpStatus.BAD_REQUEST);
        } else if (creditScore >= 500 && creditScore < 1000) {
            if (monthlyIncome < 5000) {
                return new ResponseEntity<>("Credit application approved for customer with id: " + customerId + " and assigned limit of 10000 TL.", HttpStatus.OK);
            } else if (monthlyIncome >= 5000 && monthlyIncome < 10000) {
                return new ResponseEntity<>("Credit application approved for customer with id: " + customerId + " and assigned limit of 20000 TL.", HttpStatus.OK);
            } else if (monthlyIncome >= 10000) {
                double limit = monthlyIncome * creditLimitMultiplier / 2;
                return new ResponseEntity<>("Credit application approved for customer with id: " + customerId + " and assigned limit of " + limit + " TL.", HttpStatus.OK);
            }
        } else if (creditScore >= 1000) {
        	double limit = monthlyIncome * creditLimitMultiplier;
            return new ResponseEntity<>("Credit application approved for customer with id: " + customerId + " and assigned limit of " + limit + " TL.", HttpStatus.OK);
        }

        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
