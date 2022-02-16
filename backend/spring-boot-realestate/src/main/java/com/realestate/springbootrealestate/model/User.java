package com.realestate.springbootrealestate.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
//@Data annotáció bugos a ManyToOne kapcsolatnál Lomboknál, így helyette ilyenkor Getter, Setter annotáció ajánlott
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Property> proterties;

}
