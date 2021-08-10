package com.cotto.petclinic.controllers;

import com.cotto.petclinic.model.Owner;
import com.cotto.petclinic.model.Pet;
import com.cotto.petclinic.model.PetType;
import com.cotto.petclinic.services.OwnerService;
import com.cotto.petclinic.services.PetService;
import com.cotto.petclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.persistence.metamodel.Bindable;
import javax.validation.Valid;
import java.util.Collection;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.Set;

@Controller
@ActiveProfiles("springdatajpa")
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private static final String CREATE_OR_UPDATE_PET_FORM_VIEW = "pets/createOrUpdatePetForm";

    private final PetService petService;
    private final PetTypeService petTypeService;
    private final OwnerService ownerService;

    public PetController(PetService petService, PetTypeService petTypeService, OwnerService ownerService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes(){
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") Long ownerId){
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder webDataBinder){
        webDataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String initCreationForm(@PathVariable Long ownerId, Model model){
        Owner owner = ownerService.findById(ownerId);
        Pet pet = new Pet();
        pet.setOwner(owner);
        owner.getPets().add(pet);
        model.addAttribute("pet", pet);
        return CREATE_OR_UPDATE_PET_FORM_VIEW;
    }

    @PostMapping("/pets/new")
    public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return CREATE_OR_UPDATE_PET_FORM_VIEW;
        }
        pet.setOwner(owner);
        Pet savedPet = petService.save(pet);
        return "redirect:/owners/" + savedPet.getOwner().getId();
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable Long ownerId, @PathVariable Long petId, Model model){
        Owner owner = ownerService.findById(ownerId);
        Optional<Pet> petOptional = owner.getPets().stream().filter(p -> p.getId() == petId).findFirst();
        if(petOptional.isPresent()){
            model.addAttribute("pet", petOptional.get());
            return CREATE_OR_UPDATE_PET_FORM_VIEW;
        } else {
            throw new EntityNotFoundException("pet with id of " + petId + " not found in owner with id " + ownerId);
        }
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(Owner owner, @Valid Pet pet, BindingResult bindingResult, @PathVariable Long petId){
        if(bindingResult.hasErrors()){
            return CREATE_OR_UPDATE_PET_FORM_VIEW;
        }
        pet.setOwner(owner);
        Pet savedPet = petService.save(pet);
        return "redirect:/owners/" + savedPet.getOwner().getId();
    }
}
