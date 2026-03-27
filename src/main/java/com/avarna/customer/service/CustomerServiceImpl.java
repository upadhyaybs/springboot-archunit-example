package com.avarna.customer.service;

import com.avarna.customer.domain.Customer;
import com.avarna.customer.entity.CustomerEntity;
import com.avarna.customer.exception.CustomerNotFoundException;
import com.avarna.customer.repository.ICustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService{

    final ICustomerRepository repository;
    final ModelMapper mapper;

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity=mapper.map(customer,CustomerEntity.class);
        repository.save(entity);
        customer.setId(entity.getId());
        return customer;
    }

    @Override
    public Customer findById(Long id) {
        CustomerEntity entity=repository.findById(id).orElseThrow(() -> new CustomerNotFoundException("id",id.toString()));
        return mapper.map(entity,Customer.class);
    }

    @Override
    public List<Customer> listAll() {
        List<CustomerEntity> customerEntityList=  repository.findAll();
        return Arrays.asList(mapper.map(customerEntityList,Customer[].class));
    }

    @Override
    public Customer update(Customer customer) {
        if (customer.getId() == null) {
            throw new IllegalArgumentException("Customer id is required for update");
        }
        repository.findById(customer.getId())
                .orElseThrow(() -> new CustomerNotFoundException("id", customer.getId().toString()));
        CustomerEntity entity = mapper.map(customer, CustomerEntity.class);
        repository.save(entity);
        customer.setId(entity.getId());
        return customer;
    }

    @Override
    public void deleteById(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("id", id.toString()));
        repository.deleteById(id);
    }
}
