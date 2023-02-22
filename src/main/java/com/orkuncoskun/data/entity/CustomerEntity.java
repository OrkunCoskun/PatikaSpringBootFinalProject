package com.orkuncoskun.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Data
@NoArgsConstructor
@Builder
@Log4j2
@Table(name = "customers")
public class CustomerEntity extends BaseEntity implements Serializable {

	@NotBlank
    @Size(min = 11, max = 11)
    @Column(name = "identity_number")
    private String identityNumber;
    
	@NotBlank
    @Size(min = 1, max = 30)
    @Column(name = "name")
    private String name;
    
	@NotBlank
    @Size(min = 1, max = 30)
    @Column(name = "surname")
    private String surname;
    
    @NotNull
    @Column(name = "credit_score")
    private Integer creditScore;
    
    @NotNull
    @Column(name = "monthly_income")
    private Integer monthlyIncome;
    
    @NotBlank
    @Size(min = 9, max = 20)
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @NotNull
    @Past
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    public CustomerEntity(String identityNumber, String name, String surname,Integer creditScore, Integer monthlyIncome, String phoneNumber, LocalDate dateOfBirth) {
        this.identityNumber = identityNumber;
        this.name = name;
        this.surname = surname;
        this.creditScore = creditScore;
        this.monthlyIncome = monthlyIncome;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }
}