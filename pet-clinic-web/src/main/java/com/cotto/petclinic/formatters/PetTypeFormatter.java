package com.cotto.petclinic.formatters;

import com.cotto.petclinic.model.PetType;
import com.cotto.petclinic.services.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public PetType parse(String text, Locale locale) throws ParseException {
        Collection<PetType> petTypes = petTypeService.findAll();

        return petTypes.stream().filter(petType -> petType.getName().equals(text))
                .findFirst().orElseThrow(() -> new ParseException("Pet Type not found: " + text, 0));
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }
}
