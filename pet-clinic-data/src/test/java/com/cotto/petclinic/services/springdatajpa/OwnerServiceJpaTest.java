package com.cotto.petclinic.services.springdatajpa;

import com.cotto.petclinic.model.Owner;
import com.cotto.petclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceJpaTest {

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerServiceJpa ownerServiceJpa;

    final Long ownerId = 1L;
    final String lastName = "Smith";

    Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        owner.setId(ownerId);
        owner.setLastName(lastName);
    }

    @Test
    void findAll() {
        Set<Owner> owners = new HashSet<>();
        owners.add(owner);

        when(ownerRepository.findAll()).thenReturn(owners);

        Set<Owner> ownersReturned = ownerServiceJpa.findAll();
        assertEquals(1, owners.size());
    }

    @Test
    void deleteById() {
        ownerServiceJpa.deleteById(ownerId);

        verify(ownerRepository, times(1)).deleteById(ownerId);
    }

    @Test()
    void delete() {
        ownerServiceJpa.delete(owner);

        verify(ownerRepository, times(1)).delete(owner);
    }

    @Test
    void saveExistingId() {

        when(ownerRepository.save(any())).thenReturn(owner);

        Owner savedOwner = ownerServiceJpa.save(owner);

        assertEquals(ownerId, savedOwner.getId());
    }

    @Test
    void saveNoId() {
        when(ownerRepository.save(any())).thenReturn(owner);

        Owner savedOwner = ownerServiceJpa.save(owner);

        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(ownerId)).thenReturn(java.util.Optional.ofNullable(owner));

        Owner owner = ownerServiceJpa.findById(ownerId);

        assertNotNull(owner);
        assertEquals(ownerId, owner.getId());
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(lastName)).thenReturn(java.util.Optional.ofNullable(owner));

        Owner owner = ownerServiceJpa.findByLastName(lastName);

        assertNotNull(owner);
        assertEquals(ownerId, owner.getId());
    }

    @Test
    void findByLastNameNotFound() {
        when(ownerRepository.findByLastName("foo")).thenReturn(Optional.empty());

        Owner owner = ownerServiceJpa.findByLastName("foo");

        assertNull(owner);
    }
}