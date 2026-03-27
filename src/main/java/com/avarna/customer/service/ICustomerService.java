package com.avarna.customer.service;

import com.avarna.customer.domain.Customer;

import java.util.List;

public interface ICustomerService {

    Customer save(Customer customer);

    Customer findById(Long id);

    List<Customer> listAll();

    Customer update(Customer customer);

    void deleteById(Long id);
}
