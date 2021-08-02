package com.cotto.petclinic.bootstrap;

import com.cotto.petclinic.model.Owner;
import com.cotto.petclinic.model.Vet;
import com.cotto.petclinic.services.OwnerService;
import com.cotto.petclinic.services.VetService;
import com.cotto.petclinic.services.map.OwnerServiceMap;
import com.cotto.petclinic.services.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {


    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Scott");

        ownerService.save(owner1);

        Vet vet1 = new Vet();
        vet1.setFirstName("W.B.");
        vet1.setLastName("Yeats");

        vetService.save(vet1);
    }
}
