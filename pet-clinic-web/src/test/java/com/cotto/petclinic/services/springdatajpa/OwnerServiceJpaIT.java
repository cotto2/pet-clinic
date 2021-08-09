package com.cotto.petclinic.services.springdatajpa;

import com.cotto.petclinic.model.Owner;
import com.cotto.petclinic.model.Pet;
import com.cotto.petclinic.model.PetType;
import com.cotto.petclinic.repositories.OwnerRepository;
import com.cotto.petclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("springdatajpa")
class OwnerServiceJpaIT {

    @Autowired
    OwnerService ownerService;

    @Test
    void findAllByLastNameLike() {
        PetType dog = new PetType();
        dog.setName("dog");


        PetType cat = new PetType();
        cat.setName("cat");


        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Scott");
        owner1.setAddress("1431 Harmony Ln");
        owner1.setCity("Annapolis");
        owner1.setTelephone("410-757-7104");

        Pet mikesPet = new Pet();
        mikesPet.setOwner(owner1);
        mikesPet.setBirthDate(LocalDate.now());
        mikesPet.setName("Kazbo");
        mikesPet.setPetType(dog);
        owner1.getPets().add(mikesPet);


        List<Owner> ownerList = ownerService.findAllByLastNameLike("Scott");
        assertEquals(1, ownerList.size());


    }
}