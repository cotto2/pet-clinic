package com.cotto.petclinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(exclude = {"pet"})
public class Visit extends BaseEntity {

    private LocalDate date;
    private String description;
    @ManyToOne
    private Pet pet;

}
