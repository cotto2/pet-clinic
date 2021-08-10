package com.cotto.petclinic.controllers;

import com.cotto.petclinic.model.Owner;
import com.cotto.petclinic.model.Pet;
import com.cotto.petclinic.model.PetType;
import com.cotto.petclinic.model.Visit;
import com.cotto.petclinic.services.PetService;
import com.cotto.petclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    private static final String CREATE_OR_UPDATE_VISIT_VIEW = "pets/createOrUpdateVisitForm";

    @Mock
    PetService petService;

    @Mock
    VisitService visitService;

    @InjectMocks
    VisitController visitController;

    MockMvc mockMvc;

    Owner owner;
    Pet pet;
    Visit visit;
    Set<PetType> petTypes;
    final Long id1 = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();

        owner = new Owner();
        owner.setId(id1);

        petTypes = new HashSet<>();
        PetType petType = new PetType();
        petType.setName("Dog");
        petType.setId(id1);
        petTypes.add(petType);

        pet = new Pet();
        pet.setPetType(petType);
        pet.setId(id1);
        pet.setName("charles");
        pet.setOwner(owner);

        owner.getPets().add(pet);

        visit = new Visit();
        visit.setId(id1);
    }

    @Test
    void initCreationForm() throws Exception {
        when(petService.findById(anyLong())).thenReturn(pet);

        mockMvc.perform(get("/owners/" + id1 + "/pets/" + id1 + "/visits/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(CREATE_OR_UPDATE_VISIT_VIEW));

        verify(petService, times(1)).findById(anyLong());
    }

    @Test
    void processCreationForm() throws Exception{
        when(visitService.save(any(Visit.class))).thenReturn(visit);

        mockMvc.perform(post("/owners/" + id1 + "/pets/" + id1 + "/visits/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + id1));

        verify(visitService).save(any(Visit.class));
    }
}