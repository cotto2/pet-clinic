package com.cotto.petclinic.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public class Person extends BaseEntity{

    private String firstName;
    private String lastName;

}
