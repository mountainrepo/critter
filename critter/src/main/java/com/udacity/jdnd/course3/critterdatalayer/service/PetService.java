package com.udacity.jdnd.course3.critterdatalayer.service;

import com.udacity.jdnd.course3.critterdatalayer.entity.Pet;
import java.util.*;

public interface PetService {

    public Pet savePet(Pet newPet) throws Exception;

    public Pet getPet(Long petId);

    public List<Pet> getPetsByOwner(Long customerId);

    public List<Pet> getPets();
}
