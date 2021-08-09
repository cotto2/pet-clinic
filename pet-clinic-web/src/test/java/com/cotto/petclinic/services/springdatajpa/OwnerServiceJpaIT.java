package com.cotto.petclinic.services.springdatajpa;

import com.cotto.petclinic.model.Owner;
import com.cotto.petclinic.model.Pet;
import com.cotto.petclinic.model.PetType;
import com.cotto.petclinic.repositories.OwnerRepository;
import com.cotto.petclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeAll;
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
    void testFindAllByLastNameLikeExact() {
        List<Owner> ownerList = ownerService.findAllByLastNameLike("Scott");
        assertEquals(1, ownerList.size());
    }

    @Test
    void testFindAllByLastNameLike() {
        List<Owner> ownerList = ownerService.findAllByLastNameLike("S%");
        assertEquals(1, ownerList.size());
    }

    @Test
    void testFindAllByLastNameLikeAll() {
        List<Owner> ownerList = ownerService.findAllByLastNameLike("");
        assertEquals(1, ownerList.size());
    }

}