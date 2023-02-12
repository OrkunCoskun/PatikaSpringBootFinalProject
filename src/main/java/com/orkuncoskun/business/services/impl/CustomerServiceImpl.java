package com.orkuncoskun.business.services.impl;

import com.orkuncoskun.business.dto.CustomerDto;
import com.orkuncoskun.business.services.CustomerServices;
import com.orkuncoskun.data.entity.CustomerEntity;
import com.orkuncoskun.exception.ResourceNotFoundException;
import com.orkuncoskun.data.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerServices {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;



    //LIST
    // http://localhost:8080/api/v1/customers
    @GetMapping("/customers")
    @Override
    public List<CustomerDto> getAllCustomers(){
        List<CustomerDto> listDto = new ArrayList<>();
        Iterable<CustomerEntity> list = customerRepository.findAll();
        for (CustomerEntity entity : list) {
            CustomerDto customerDto = EntityToDto(entity);//model
            listDto.add(customerDto);
        }
        return listDto;
    }

    //FIND
    // http://localhost:8080/api/v1/customers/1
    @GetMapping("/customers/{id}")
    @Override
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable(name = "id") Long id) {

        CustomerEntity customer=
                customerRepository
                        .findById(id)
                        .orElseThrow(
                                ()->new ResourceNotFoundException("Customer not exist with id :" + id)
                        );

        CustomerDto customerDto = EntityToDto(customer);//model
        return ResponseEntity.ok(customerDto);
    }
    
    //SAVE
    // http://localhost:8080/api/v1/customers
    @PostMapping("/customers")
    public CustomerDto createCustomers(@RequestBody CustomerDto customerDto) { //@RequestBody
//    	if (customerDto.getCreditScore()  < 500) {
//           System.out.println("Customer has been rejected due to a low credit score.");
//        }
        CustomerEntity customerEntity = DtoToEntity(customerDto);//ModelMapper
        customerRepository.save(customerEntity);
        return customerDto;
    }

    //DELETE
    // http://localhost:8080/api/v1/customers
    @DeleteMapping("/customers/{id}")
    @Override
    public ResponseEntity<Map<String, Boolean>> deleteCustomer(@PathVariable(name = "id") Long id){
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist with id :" + id));

        customerRepository.delete(customer);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    //UPDATE
    // http://localhost:8080/api/v1/customers
    @PutMapping("/customers/{id}")
    @Override
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable(name = "id") Long id, @RequestBody CustomerDto customerDetails){
        CustomerEntity customerEntity = DtoToEntity(customerDetails);//ModelMapper

        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist with id :" + id));

        customer.setIdentityNumber( customerEntity.getIdentityNumber());
        customer.setName( customerEntity.getName());
        customer.setSurname(customerEntity.getSurname());
        customer.setCreditScore(customerEntity.getCreditScore());
        customer.setMonthlyIncome(customerEntity.getMonthlyIncome());
        customer.setPhoneNumber(customerEntity.getPhoneNumber());
        customer.setDateOfBirth(customerEntity.getDateOfBirth());

        CustomerEntity updatedCustomer = customerRepository.save(customer);
        CustomerDto customerDto = EntityToDto(updatedCustomer);//model
        return ResponseEntity.ok(customerDto);
    }



    ////////////////////////////////////
    //Model Mapper Entity ==> Dto
    @Override
    public CustomerDto EntityToDto(CustomerEntity customerEntity) {
        CustomerDto customerDto = modelMapper.map(customerEntity, CustomerDto.class);
        return customerDto;
    }

    //Model Mapper Dto  ==> Entity
    @Override
    public CustomerEntity DtoToEntity(CustomerDto customerDto) {
        CustomerEntity customerEntity = modelMapper.map(customerDto, CustomerEntity.class);
        return customerEntity;
    }
}
