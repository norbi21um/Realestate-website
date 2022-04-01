package com.realestate.springbootrealestate.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "district")
    private String district;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "districtTest")
    private Set<Property> properties;

}
