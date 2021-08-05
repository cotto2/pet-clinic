package com.cotto.petclinic.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Speciality extends BaseEntity{

    private String description;

}
