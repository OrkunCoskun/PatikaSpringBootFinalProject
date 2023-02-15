# Credit Application System
- Java 17
- Spring Framework
- Spring Boot
- Spring MVC
- Spring Data (Jpa Hibernate, MySQL)
- Spring Rest (http status)
- Thymeleaf

---


### Project Steps
1. @Bean (ModelMapper)
2. Dto
3. BaseEntity (@MappedSuperclass)
4. CustomerEntity (@Entity)
5. CustomerRepository (@Repository)
6. CustomerServices (interface)
7. CustomerServiceImpl(@Service)
8. ResourceNotFoundException(@ResponseStatus)
9. CustomerController(@Controller)
10. CustomerRestController(@RestController)

---

### Unit Test
1. TestCrud (interface)
2. @SpringBootTest

---

### Auditing
1. AuditorAwareBean
2. AuditorAwareImpl (implements AuditorAware)
3. @SpringBootApplication


## Postman
```sh
// SAVE
http://localhost:8080/api/v1/customers


//LIST
http://localhost:8080/api/v1/customers


//FIND
http://localhost:8080/api/v1/customers/1


//UPDATE
http://localhost:8080/api/v1/customers/1


//DELETE
http://localhost:8080/api/v1/customers/1

//APPLY FOR CREDIT
http://localhost:8080/api/v1/apply/1
```

