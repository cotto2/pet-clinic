package com.cotto.petclinic.controllers;

import com.cotto.petclinic.model.Pet;
import com.cotto.petclinic.model.Visit;
import com.cotto.petclinic.services.PetService;
import com.cotto.petclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("owners/{ownerId}/pets/{petId}/visits")
public class VisitController {

    private static final String CREATE_OR_UPDATE_VISIT_VIEW = "pets/createOrUpdateVisitForm";

    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder webDataBinder){
        webDataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("pet")
    public Pet addPet(@PathVariable Long petId){
        return petService.findById(petId);
    }

    @GetMapping("/new")
    public String initCreationForm(Pet pet, Model model){
        Visit visit = new Visit();
        visit.setPet(pet);
        pet.getVisits().add(visit);
        model.addAttribute("visit", visit);
        return CREATE_OR_UPDATE_VISIT_VIEW;
    }

    @PostMapping("/new")
    public String postCreationForm(Pet pet, @Valid Visit visit, BindingResult bindingResult, @PathVariable Long ownerId){
        if(bindingResult.hasErrors()){
            return CREATE_OR_UPDATE_VISIT_VIEW;
        }
        Visit savedVisit = visitService.save(visit);
        return "redirect:/owners/" + ownerId;
    }
}
