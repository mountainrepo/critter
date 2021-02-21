package com.udacity.jdnd.course3.critterdatalayer.repository;

import com.udacity.jdnd.course3.critterdatalayer.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("SELECT p FROM Pet p WHERE p.ownerId = :customerId")
    public List<Pet> findPetsByOwner(Long customerId);
}
