package com.cotto.petclinic.controllers;

import com.cotto.petclinic.model.Owner;
import com.cotto.petclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping({"", "/index"})
    public String listOwners(Model model){
        model.addAttribute("owners", ownerService.findAll() );
        return "owners/index";
    }

    @GetMapping("/find")
    public String findOwner(Model model){
        return "owners/find";
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showDetails(@PathVariable Long ownerId){
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        Owner owner = ownerService.findById(ownerId);
        mav.addObject(owner);
        return mav;
    }
}
