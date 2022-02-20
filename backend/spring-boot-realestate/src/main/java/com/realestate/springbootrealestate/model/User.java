package com.realestate.springbootrealestate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    @JsonIgnore
    private String email;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private Set<Property> proterties;

}
