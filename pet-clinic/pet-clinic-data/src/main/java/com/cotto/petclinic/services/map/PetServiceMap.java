package com.cotto.petclinic.services.map;

import com.cotto.petclinic.model.Pet;
import com.cotto.petclinic.model.Pet;
import com.cotto.petclinic.services.CrudService;
import com.cotto.petclinic.services.PetService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PetServiceMap extends AbstractMapService<Pet,Long> implements PetService {
    @Override
    public Set<Pet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Pet object) {
        super.delete(object);
    }

    @Override
    public Pet save(Pet object) {
        return super.save( object);
    }

    @Override
    public Pet findById(Long id) {
        return super.findById(id);
    }
}
