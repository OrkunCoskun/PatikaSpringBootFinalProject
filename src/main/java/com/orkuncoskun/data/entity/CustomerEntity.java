package com.orkuncoskun.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Builder
@Log4j2
@Table(name = "customers")
public class CustomerEntity extends BaseEntity implements Serializable {

    @Column(name = "identity_number")
    private String identityNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "credit_score")
    private Double creditScore;
    @Column(name = "monthly_income")
    private Double monthlyIncome;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    public CustomerEntity(String identityNumber, String name, String surname,Double creditScore, Double monthlyIncome, String phoneNumber, LocalDate dateOfBirth) {
        this.identityNumber = identityNumber;
        this.name = name;
        this.surname = surname;
        this.creditScore = creditScore;
        this.monthlyIncome = monthlyIncome;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }
}