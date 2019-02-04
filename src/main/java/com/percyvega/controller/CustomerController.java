package com.percyvega.controller;

import com.percyvega.model.Customer;
import com.percyvega.model.CustomerRepository;
import com.percyvega.service.CustomerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> findAll() {
        return customerService.findAll();
    }
    
    @GetMapping("/lastName/{lastName}")
    public List<Customer> findByLastName(@PathVariable("lastName") String lastName) {
        return customerService.findByLastName(lastName);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Customer> findById(@PathVariable("id") Long id) {
        Optional<Customer> customerOptional = customerService.findById(id);
        if(customerOptional.isPresent()) {
            return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
