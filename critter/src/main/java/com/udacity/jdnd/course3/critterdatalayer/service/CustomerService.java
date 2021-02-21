package com.udacity.jdnd.course3.critterdatalayer.service;

import com.udacity.jdnd.course3.critterdatalayer.entity.Customer;

import java.util.*;

public interface CustomerService {

    public Customer saveCustomer(Customer newCustomer, List<Long> petIdList);

    public List<Customer> getAllCustomers();

    public Customer getOwnerByPet(Long petId);
}
