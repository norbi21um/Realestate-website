package com.realestate.springbootrealestate.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.realestate.springbootrealestate.model.Property;
import com.realestate.springbootrealestate.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String username;

    private String email;

    private Set<Property> proterties;

    private Set<Role> roles = new HashSet<>();

    private String phoneNumber;

    private String firstName;

    private String lastName;
}
