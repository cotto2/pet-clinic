package com.cotto.petclinic.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Pet extends BaseEntity{

    private String name;
    @ManyToOne
    private PetType petType;
    @ManyToOne
    private Owner owner;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
    private Set<Visit> visits = new HashSet<>();
}
