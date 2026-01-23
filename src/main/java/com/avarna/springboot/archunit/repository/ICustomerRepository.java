package com.tp.springboot.archunit.repository;

import com.tp.springboot.archunit.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends JpaRepository<CustomerEntity,Long> {

}
