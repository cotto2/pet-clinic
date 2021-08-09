package com.cotto.petclinic.controllers;

import com.cotto.petclinic.model.Owner;
import com.cotto.petclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    public static final String CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController ownerController;

    Set<Owner> ownerSet;
    List<Owner> ownerList;
    Owner owner1;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        ownerList = new LinkedList<>();
        ownerSet = new HashSet<>();
        owner1 = new Owner();
        owner1.setId(1L);
        owner1.setAddress("1431 Harmony Ln");
        Owner owner2 = new Owner();
        owner2.setId(2L);
        ownerSet.add(owner1);
        ownerSet.add(owner2);
        ownerList.add(owner1);
        ownerList.add(owner2);


        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }


    @Test
    void findOwner() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void processFindFormReturnMany() throws Exception{
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(ownerList);

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));
    }

    @Test
    void processFindFormReturnOne() throws Exception{
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Arrays.asList(owner1));

        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
    }

    @Test
    void showOwnerById() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner1);

        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))));
    }

    @Test
    void initCreateForm() throws Exception{
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(CREATE_OR_UPDATE_OWNER_FORM))
                .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void processCreateForm() throws Exception{
        when(ownerService.save(ArgumentMatchers.any())).thenReturn(owner1);

        mockMvc.perform(post("/owners/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + owner1.getId()));

        verify(ownerService).save(ArgumentMatchers.any());
    }

    @Test
    void initUpdateForm() throws Exception{
        when(ownerService.findById(anyLong())).thenReturn(owner1);

        mockMvc.perform(get("/owners/" + owner1.getId() + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name(CREATE_OR_UPDATE_OWNER_FORM))
                .andExpect(model().attributeExists("owner"));

        verify(ownerService).findById(anyLong());
    }

    @Test
    void processUpdateForm() throws Exception{
        when(ownerService.save(ArgumentMatchers.any())).thenReturn(owner1);

        mockMvc.perform(post("/owners/" + owner1.getId() + "/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + owner1.getId()));

        verify(ownerService).save(ArgumentMatchers.any());
    }
}