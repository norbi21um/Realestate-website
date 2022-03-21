package com.realestate.springbootrealestate.service;

import com.realestate.springbootrealestate.dto.response.UserResponse;
import com.realestate.springbootrealestate.model.Property;
import com.realestate.springbootrealestate.model.Role;
import com.realestate.springbootrealestate.model.User;
import com.realestate.springbootrealestate.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public UserResponse getUserById(Long id){
        return convertToUserResponse(userRepository.findById(id).orElse(null), id);
    }

    public UserResponse convertToUserResponse(User user, Long id){
        if(user == null){
            throw new EntityNotFoundException("Property not foudn with the id of: " + id);
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setProterties(user.getProterties());
        userResponse.setRoles(user.getRoles());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());

        return userResponse;
    }
}
