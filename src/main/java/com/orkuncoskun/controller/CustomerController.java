package com.orkuncoskun.controller;

import com.orkuncoskun.business.services.CustomerServices;
import com.orkuncoskun.data.entity.CustomerEntity;
import com.orkuncoskun.data.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({ "/api/v1", "/" })
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
	@Autowired
	CustomerServices customerServices;

	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("/apply")
	public ModelAndView apply(@RequestParam("customerId") @PathVariable Long customerId, Model model) {
	    model.addAttribute("customerId", customerId);
	    ModelAndView mv = new ModelAndView("credit-result");
		CustomerEntity customer = customerRepository.findById(customerId).orElse(null);

		if (customer == null) {
			mv.addObject("status", "NOT_FOUND");
			mv.addObject("message", "Customer not found with id: " + customerId);
			return mv;
		}

		double creditScore = customer.getCreditScore();
		double monthlyIncome = customer.getMonthlyIncome();
		int creditLimitMultiplier = 4;

		mv.addObject("customerId", customerId);
		mv.addObject("phoneNumber", customer.getPhoneNumber());

		if (creditScore < 500) {
			mv.addObject("status", "REJECTED");
			mv.addObject("message", "Credit score is below 500, credit application rejected.");
			return mv;
		} else if (creditScore >= 500 && creditScore < 1000) {
			if (monthlyIncome < 5000) {
				mv.addObject("status", "APPROVED");
				mv.addObject("message", "Credit application approved and assigned limit of 10000 TL.");
				mv.addObject("limit", 10000);
				return mv;
			} else if (monthlyIncome >= 5000 && monthlyIncome < 10000) {
				mv.addObject("status", "APPROVED");
				mv.addObject("message", "Credit application approved and assigned limit of 20000 TL.");
				mv.addObject("limit", 20000);
				return mv;
			} else if (monthlyIncome >= 10000) {
				double limit = monthlyIncome * creditLimitMultiplier / 2;
				mv.addObject("status", "APPROVED");
				mv.addObject("message", "Credit application approved and assigned limit of " + limit + " TL.");
				mv.addObject("limit", limit);
				return mv;
			}
		} else if (creditScore >= 1000) {
			double limit = monthlyIncome * creditLimitMultiplier;
			mv.addObject("status", "APPROVED");
			mv.addObject("message", "Credit application approved and assigned limit of " + limit + " TL.");
			mv.addObject("limit", limit);
			return mv;
		}

		mv.addObject("status", "INTERNAL_ERROR");
		mv.addObject("message", "Something went wrong.");
		return mv;
	}

	// http://localhost:8080/api/v1/viewCustomers
	@GetMapping({ "/viewCustomers", "/", "/list" })
	public ModelAndView showCustomersView(Model model) {
		ModelAndView mv = new ModelAndView("list-customers");
		List<CustomerEntity> customers = customerRepository.findAll();
		mv.addObject("customers", customers);
		return mv;
	}

	@GetMapping("/addCustomerForm")
	public ModelAndView addnewCustomersView() {
		ModelAndView mv = new ModelAndView("add-customers");
		CustomerEntity newCustomer = new CustomerEntity();
		mv.addObject("customer", newCustomer);
		return mv;
	}

	@PostMapping(value = "/saveCustomer")
	public ModelAndView saveCustomerView(@ModelAttribute CustomerEntity customer) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("list-customers");
		customerRepository.save(customer);
		mv.addObject("customers", customerServices.getAllCustomers());

		return mv;
	}

	@GetMapping(value = "/updateCustomer")
	public ModelAndView updateCustomer(@RequestParam long customerId) {
		ModelAndView mv = new ModelAndView("update-customers");
		Optional<CustomerEntity> customer = customerRepository.findById(customerId);
		mv.addObject("customer", customer);
		return mv;
	}

	// !!!!!!!!!!!!!!!!!!!There is an error it does not update the customer
	@PostMapping(value = "updateNewCustomer")
	public ModelAndView updateNewCustomer(@ModelAttribute CustomerEntity customer) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("customer", customer);
		mv.setViewName("list-customers");
		customerRepository.save(customer);
		mv.addObject("customers", customerServices.getAllCustomers());
		return mv;
	}

	@GetMapping(value = "/deleteCustomer")
	public ModelAndView deleteCustomerView(@RequestParam long customerId) throws Throwable {
		ModelAndView mv = new ModelAndView("list-customers");
		customerRepository.deleteById(customerId);
		mv.addObject("customers", customerServices.getAllCustomers());
		return mv;
	}
}
