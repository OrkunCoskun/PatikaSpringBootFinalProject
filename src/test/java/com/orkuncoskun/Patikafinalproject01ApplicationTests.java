package com.orkuncoskun;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.orkuncoskun.data.entity.CustomerEntity;
import com.orkuncoskun.data.repository.CustomerRepository;
import com.orkuncoskun.test.TestCrud;

@SpringBootTest
class Patikafinalproject01ApplicationTests implements TestCrud{

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
	String date = "2000-02-02";
	LocalDate dateOfBirth = LocalDate.parse(date, formatter);

	@Autowired
    CustomerRepository customerRepository;
	
	@Test
	void contextLoads() {
	}

	//CREATE
	@Test
	@Override
	public void testCreate() {
		
		CustomerEntity customerEntity = CustomerEntity
                .builder()
                .identityNumber("26716775988")
                .name("Orkun")
                .surname("Coskun ")
                .creditScore(400.0)
                .monthlyIncome(6000.0)
                .phoneNumber("05310816566")
                .dateOfBirth(dateOfBirth)
                .build();
		customerRepository.save(customerEntity);

        //nesne null ise assertionError hatasını göndersin
        //1. kayda göre verileri getir
        assertNotNull(customerRepository.findById(1L).get());
		
	}

	//LIST
    @Test
    @Override
    public void testList() {
        List<CustomerEntity> list = customerRepository.findAll();

        //eğer Sıfırdan büyükse liste vardır
        assertThat(list).size().isGreaterThan(0);
    }

    //FINDBYID
    @Test
    @Override
    public void testFindById() {
    	CustomerEntity customerEntity = customerRepository.findById(1L).get();

        //Orkun adında kayıt var mı yok mu
        assertEquals("Orkun", customerEntity.getName());
    }

    //UPDATE
    @Test
    @Override
    public void testUpdate() {
    	CustomerEntity customerEntity = customerRepository.findById(1L).get();
    	customerEntity.setName("Orkun55");
        customerRepository.save(customerEntity);
        //Eşit değilse bir sorun olmayacak ama eşitse exception fırlatsın
        assertNotEquals("Orkun", customerRepository.findById(1L).get().getName());
    }

    //DELETE
    @Test
    @Override
    public void testDelete() {
    	customerRepository.deleteById(1L);
        //isFalse
        assertThat(customerRepository.existsById(1L)).isFalse();
    }

}
