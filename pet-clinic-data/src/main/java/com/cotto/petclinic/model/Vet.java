package com.cotto.petclinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"specialities"})
public class Vet extends Person{

    @ManyToMany
    @JoinTable(name = "vet_specialities", joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private Set<Speciality> specialities = new HashSet<>();

    public int getNrOfSpecialities() {
        return specialities.size();
    }

    public void addSpeciality(Speciality speciality) {
        specialities.add(speciality);
    }

}
