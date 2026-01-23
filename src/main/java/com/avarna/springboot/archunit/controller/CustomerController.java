package com.tp.springboot.archunit.controller;

import com.tp.springboot.archunit.domain.Customer;
import com.tp.springboot.archunit.service.ICustomerService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService service;

    @PostMapping
    public Customer create(@RequestBody Customer customer){
        return this.service.save(customer);
    }

    @PatchMapping
    public Customer update(@RequestBody Customer customer){
        return this.service.update(customer);
    }

    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable Long id){
        this.service.deleteById(id);
    }

    @GetMapping("/id/{id}")
    public Customer findById(@PathVariable Long id){
        return this.service.findById(id);
    }

    @GetMapping
    public List<Customer> findAll(){
        return this.service.listAll();
    }


}
