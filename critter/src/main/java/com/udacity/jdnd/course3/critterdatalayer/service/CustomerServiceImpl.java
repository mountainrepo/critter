package com.udacity.jdnd.course3.critterdatalayer.service;

import com.udacity.jdnd.course3.critterdatalayer.entity.*;

import com.udacity.jdnd.course3.critterdatalayer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    @Transactional
    public Customer saveCustomer(Customer newCustomer, List<Long> petIdList) {
        Customer savedCustomer = null;

        try {
            List<Pet> petList = getPetList(petIdList);
            newCustomer.setPets(petList);

            savedCustomer = customerRepository.save(newCustomer);
        }
        catch(Exception ex) {
            throw ex;
        }

        return savedCustomer;
    }

    private List<Pet> getPetList(List<Long> petIdList) {
        if(petIdList == null) {
            return null;
        }

        List<Pet> petList = new ArrayList<Pet>(petIdList.size());

        for(Long petId : petIdList) {
            Optional<Pet> petForId = petRepository.findById(petId);

            if(petForId.isPresent()) {
                petList.add(petForId.get());
            }
        }

        return petList;
    }

    @Transactional
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = null;

        try {
            customerList = customerRepository.findAll();
        }
        catch(Exception ex) {
            throw ex;
        }

        return customerList;
    }

    @Transactional
    public Customer getOwnerByPet(Long petId) {
        Customer owner = null;

        try {
            owner = customerRepository.findCustomerByPet(petId);
        }
        catch(Exception ex) {
            throw ex;
        }

        return owner;
    }
}
