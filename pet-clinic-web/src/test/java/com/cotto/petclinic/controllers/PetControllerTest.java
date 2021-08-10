package com.cotto.petclinic.controllers;

import com.cotto.petclinic.model.Owner;
import com.cotto.petclinic.model.Pet;
import com.cotto.petclinic.model.PetType;
import com.cotto.petclinic.services.OwnerService;
import com.cotto.petclinic.services.PetService;
import com.cotto.petclinic.services.PetTypeService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    private static final String CREATE_OR_UPDATE_PET_FORM_VIEW = "pets/createOrUpdatePetForm";

    @Mock
    PetService petService;

    @Mock
    PetTypeService petTypeService;

    @Mock
    OwnerService ownerService;

    @InjectMocks
    PetController petController;

    MockMvc mockMvc;

    Owner owner;
    Pet pet;
    Set<PetType> petTypes;
    final Long id1 = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();

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
    }

    @Test
    void populatePetTypes() {
    }

    @Test
    void findOwner() {
    }

    @Test
    void initOwnerBinder() {
    }

    @Test
    void initCreationForm() throws Exception{
        when(ownerService.findById(anyLong())).thenReturn(owner);

        mockMvc.perform(get("/owners/" + id1 + "/pets/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(CREATE_OR_UPDATE_PET_FORM_VIEW))
                .andExpect(model().attributeExists("pet"));
    }

    @Test
    void processCreationForm() throws Exception {
        when(petService.save(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(post("/owners/" + id1 + "/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + id1));

        verify(petService).save(any(Pet.class));
    }

    @Test
    void initUpdateForm() throws Exception{
        when(ownerService.findById(anyLong())).thenReturn(owner);

        mockMvc.perform(get("/owners/" + id1 + "/pets/" + id1 + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name(CREATE_OR_UPDATE_PET_FORM_VIEW))
                .andExpect(model().attributeExists("pet"));
    }

    @Test
    void processUpdateForm() throws Exception {
        when(petService.save(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(post("/owners/" + id1 + "/pets/" + id1 + "/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + id1));

        verify(petService).save(any(Pet.class));
    }
}