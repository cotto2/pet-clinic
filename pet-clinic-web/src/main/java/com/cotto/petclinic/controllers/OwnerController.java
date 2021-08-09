package com.cotto.petclinic.controllers;

import com.cotto.petclinic.model.Owner;
import com.cotto.petclinic.services.OwnerService;
import org.dom4j.rule.Mode;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.awt.*;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    public static final String CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/find")
    public String initFindForm(Model model){
        model.addAttribute("owner", new Owner());
        return "owners/findOwners";
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Model model){
        if(owner.getLastName() == null){
            owner.setLastName("");
        }

        List<Owner> results = this.ownerService.findAllByLastNameLike(owner.getLastName());
        if(results.isEmpty()){
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (results.size() == 1){
            owner = results.iterator().next();
            return "redirect:/owners/" + owner.getId();
        } else {
            model.addAttribute("selections",results );
            return "owners/ownersList";
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showDetails(@PathVariable Long ownerId){
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        Owner owner = ownerService.findById(ownerId);
        mav.addObject(owner);
        return mav;
    }

    @GetMapping("/new")
    public String initCreateForm(Model model) {
        model.addAttribute("owner", new Owner());
        return CREATE_OR_UPDATE_OWNER_FORM;
    }

    @PostMapping(value = "/new")
    public String processCreateForm(@Valid Owner owner , BindingResult result){
        if(result.hasErrors()){
            return CREATE_OR_UPDATE_OWNER_FORM;
        }
        Owner savedOwner = ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();
    }

    @GetMapping("/{id}/edit")
    public String initUpdateForm(@PathVariable Long id, Model model){
        model.addAttribute("owner", ownerService.findById(id));
        return CREATE_OR_UPDATE_OWNER_FORM;
    }

    @PostMapping(value = "/{id}/edit")
    public String processUpdateForm(@Valid Owner owner, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return CREATE_OR_UPDATE_OWNER_FORM;
        }
        owner.setId(id);
        Owner savedOwner = ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();
    }



}
