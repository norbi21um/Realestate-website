package com.realestate.springbootrealestate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * District JPA Entity
 */
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

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "districtTest")
    private Set<Property> properties;

}
