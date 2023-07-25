package com.tp.springboot.archunit.service;

import com.tp.springboot.archunit.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICustomerService {

    Customer save(Customer customer);

    Customer findById(Long id);

    List<Customer> listAll();

    Customer update(Customer customer);

    void deleteById(Long id);
}
