package com.cotto.petclinic.controllers;

import com.cotto.petclinic.exceptions.OupsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OupsController {

    @GetMapping("/oups")
    public String oups(){
        throw new OupsException("this exception was thrown for fun");
    }
}
