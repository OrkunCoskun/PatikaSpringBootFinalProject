package com.orkuncoskun.controller;

import com.orkuncoskun.business.dto.CustomerDto;
import com.orkuncoskun.business.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
    @Autowired
    CustomerServices employoeeServices;


    //ROOT
    //http://localhost:8080/api/v1/index
    @GetMapping({"/index","/"}  )
    public String getRoot(){
        return "index";
    }

    //LIST
    //http://localhost:8080/api/v1/customers
    @GetMapping("/customers")
    public List<CustomerDto> getAllCustomers(){
        List<CustomerDto> customerDtoList=employoeeServices.getAllCustomers();
        return customerDtoList;
    }

    //FIND
    //http://localhost:8080/api/v1/customers/1
    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) throws Throwable {
        ResponseEntity<CustomerDto> customerDto=  employoeeServices.getCustomerById(id);
        return customerDto;
    }

    //SAVE
    //http://localhost:8080/api/v1/customers
    @PostMapping("/customers")
    public CustomerDto createCustomer(@RequestBody CustomerDto customerDto){
        employoeeServices.createCustomers(customerDto);
        return customerDto;
    }

    //UPDATE
    //http://localhost:8080/api/v1/customers/1
    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerDto>  updateCustomer(@PathVariable Long id,  @RequestBody CustomerDto customerDto) throws Throwable {
        employoeeServices.updateCustomer(id,customerDto);
        return ResponseEntity.ok(customerDto);
    }

    //DELETE
    //http://localhost:8080/api/v1/customers/1
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteCustomer(@PathVariable Long id) throws Throwable {
        employoeeServices.deleteCustomer(id);
        Map<String,Boolean> response=new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
