package com.orkuncoskun.business.services;

import com.orkuncoskun.business.dto.CustomerDto;
import com.orkuncoskun.data.entity.CustomerEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CustomerServices {

    //CRUD
    public List<CustomerDto> getAllCustomers();
    public CustomerDto createCustomers(CustomerDto customerDto);
    public ResponseEntity<CustomerDto> getCustomerById(Long id) throws Throwable;
    public ResponseEntity<CustomerDto> updateCustomer(Long id, CustomerDto customerDto) throws Throwable;
    public ResponseEntity<Map<String, Boolean>> deleteCustomer(Long id) throws Throwable;

    //model mapper
    public CustomerDto EntityToDto(CustomerEntity customerEntity);
    public CustomerEntity DtoToEntity(CustomerDto customerDto);
}
