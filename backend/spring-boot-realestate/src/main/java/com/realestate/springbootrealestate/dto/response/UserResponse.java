package com.realestate.springbootrealestate.dto.response;

import com.realestate.springbootrealestate.model.Property;
import com.realestate.springbootrealestate.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for sharing User data
 * (The "properties" set is annotated with @JsonIgnore in the User class
 * to curb a potential endless loop upon requesting data on the properties.
 * Instead of changing the fetch type to Eager in the Property class
 * I choose to create a seperate DTO for the User, since it represents a less frequently requested data)
 */
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
