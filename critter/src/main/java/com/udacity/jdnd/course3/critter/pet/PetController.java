package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critterdatalayer.entity.Pet;
import com.udacity.jdnd.course3.critterdatalayer.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @RequestMapping(method = RequestMethod.POST)
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertToPetEntity(petDTO);

        try {
            Pet savedPet = petService.savePet(pet);
            return convertToPetDTO(savedPet);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{petId}")
    public PetDTO getPet(@PathVariable Long petId) {
        Pet foundPet = petService.getPet(petId);

        return convertToPetDTO(foundPet);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<PetDTO> getPets(){
        List<Pet> petList = petService.getPets();

        List<PetDTO> petDTOList = new ArrayList<PetDTO>(petList.size());
        for(Pet pet : petList) {
            petDTOList.add(convertToPetDTO(pet));
        }

        return petDTOList;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> petList = petService.getPetsByOwner(ownerId);

        List<PetDTO> petDTOList = new ArrayList<PetDTO>(petList.size());
        for(Pet pet : petList) {
            petDTOList.add(convertToPetDTO(pet));
        }

        return petDTOList;
    }

    private Pet convertToPetEntity(PetDTO inputPetDTO) {
        Pet outPet = new Pet();
        BeanUtils.copyProperties(inputPetDTO, outPet);

        return outPet;
    }

    private PetDTO convertToPetDTO(Pet inputPet) {
        PetDTO outPetDTO = new PetDTO();
        BeanUtils.copyProperties(inputPet, outPetDTO);
        return outPetDTO;
    }    
}
