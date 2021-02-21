package com.udacity.jdnd.course3.critterdatalayer.service;

import com.udacity.jdnd.course3.critterdatalayer.entity.*;
import com.udacity.jdnd.course3.critterdatalayer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Pet savePet(Pet newPet) throws Exception {
        Pet savedPet = null;

        try {
            savedPet = petRepository.save(newPet);

            updateCustomer(savedPet.getOwnerId(), savedPet);
        }
        catch(Exception ex) {
            throw ex;
        }

        return savedPet;
    }

    private void updateCustomer(Long customerId, Pet pet) throws Exception {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(!customer.isPresent()) {
            throw new Exception("Customer not found");
        }

        List<Pet> petList = null;
        if(customer.get().getPets() == null) {
            petList = new LinkedList<Pet>();
        }
        else {
            petList = new LinkedList<Pet>();
            petList.addAll(customer.get().getPets());
        }
        petList.add(pet);

        customer.get().setPets(petList);

        customerRepository.save(customer.get());
    }

    @Transactional
    public Pet getPet(Long petId) {
        Optional<Pet> foundPet = null;

        try {
            foundPet = petRepository.findById(petId);
        }
        catch(Exception ex) {
            throw ex;
        }

        if(foundPet.isPresent()) {
            return foundPet.get();
        }
        else {
            return null;
        }
    }

    @Transactional
    public List<Pet> getPetsByOwner(Long customerId) {
        List<Pet> petList = null;

        try {
            petList = petRepository.findPetsByOwner(customerId);
        }
        catch(Exception ex) {
            throw ex;
        }

        return petList;
    }

    @Transactional
    public List<Pet> getPets() {
        List<Pet> petList = null;

        try {
            petList = petRepository.findAll();
        }
        catch(Exception ex) {
            throw ex;
        }

        return petList;
    }
}
