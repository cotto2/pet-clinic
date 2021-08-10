package com.cotto.petclinic.bootstrap;

import com.cotto.petclinic.model.Owner;
import com.cotto.petclinic.model.Pet;
import com.cotto.petclinic.model.PetType;
import com.cotto.petclinic.model.Vet;
import com.cotto.petclinic.services.OwnerService;
import com.cotto.petclinic.services.PetService;
import com.cotto.petclinic.services.PetTypeService;
import com.cotto.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {


    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final PetService petService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, PetService petService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public void run(String... args) throws Exception {
        PetType dog = new PetType();
        dog.setName("dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("cat");
        PetType savedCatPetType = petTypeService.save(cat);


        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Scott");
        owner1.setAddress("1431 Harmony Ln");
        owner1.setCity("Annapolis");
        owner1.setTelephone("410-757-7104");

        Pet mikesPet = new Pet();
        mikesPet.setPetType(savedDogPetType);
        mikesPet.setOwner(owner1);
        mikesPet.setBirthDate(LocalDate.now());
        mikesPet.setName("Kazbo");
        owner1.getPets().add(mikesPet);


        ownerService.save(owner1);
        petService.save(mikesPet);


        Vet vet1 = new Vet();
        vet1.setFirstName("W.B.");
        vet1.setLastName("Yeats");

        vetService.save(vet1);
    }
}
