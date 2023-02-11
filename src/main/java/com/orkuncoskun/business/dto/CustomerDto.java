package com.orkuncoskun.business.dto;

//DTO: Data Transfer Object

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2
public class CustomerDto {
    private Long id;
    private String identityNumber;
    private String name;
    private String surname;
    private Double monthlyIncome;
    private String phoneNumber;
    private LocalDate dateOfBirth;
}
