package com.cotto.petclinic.controllers;

import com.cotto.petclinic.exceptions.OupsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(NumberFormatException exception){
        ModelAndView modelAndView = new ModelAndView("exceptions/400error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(OupsException.class)
    public ModelAndView handleOupsException(OupsException exception){
        ModelAndView modelAndView = new ModelAndView("exceptions/oups");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
}
