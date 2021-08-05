package com.cotto.petclinic.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class PetType extends BaseEntity{

    private String name;

}
