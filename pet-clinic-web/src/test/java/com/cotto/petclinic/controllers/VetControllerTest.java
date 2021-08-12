package com.cotto.petclinic.controllers;

import com.cotto.petclinic.model.Vet;
import com.cotto.petclinic.services.VetService;
import org.hamcrest.Matchers;
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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    @Mock
    VetService vetService;

    @InjectMocks
    VetController vetController;

    MockMvc mockMvc;

    Set<Vet> vets = new HashSet<>();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vetController).build();

        Vet vet = new Vet();
        vet.setFirstName("W.B.");
        vet.setLastName("Yeats");
        vets.add(vet);
    }

    @Test
    void listVets() throws Exception {
        when(vetService.findAll()).thenReturn(vets);

        mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(view().name("vets/vetList"))
                .andExpect(model().attributeExists("vets"));

        verify(vetService, times(1)).findAll();
    }

    @Test
    void getVetsJson() throws Exception {
        when(vetService.findAll()).thenReturn(vets);

        mockMvc.perform(get("/api/vets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));

        verify(vetService, times(1)).findAll();
    }
}