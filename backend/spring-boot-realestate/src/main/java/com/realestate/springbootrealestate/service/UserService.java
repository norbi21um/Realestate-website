package com.realestate.springbootrealestate.service;

import com.realestate.springbootrealestate.dto.response.UserResponse;
import com.realestate.springbootrealestate.model.Click;
import com.realestate.springbootrealestate.model.Property;
import com.realestate.springbootrealestate.model.Role;
import com.realestate.springbootrealestate.model.User;
import com.realestate.springbootrealestate.repository.ClickRepository;
import com.realestate.springbootrealestate.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * User service
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final StatisticsService statisticsService;
    private final PropertyService propertyService;
    private final UserDetailsServiceImpl userDetailsService;


    /**
     * Gets user by its id from the database
     * @param id id
     * @return user
     */
    public UserResponse getUserById(Long id){
        return convertToUserResponse(userRepository.findById(id).orElse(null), id);
    }

    /**
     * Builds a UserResponse data transfer object from a User
     * @param user user
     * @param id id
     * @return UserResponse
     */
    public UserResponse convertToUserResponse(User user, Long id){
        if(user == null){
            throw new EntityNotFoundException("Property not foudn with the id of: " + id);
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());

        userResponse.setProterties(
                propertyService.convertToPropertyListResponse(new ArrayList<>(user.getProterties()))
        );

        userResponse.setRoles(user.getRoles());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setOptimalTimeToPost(statisticsService.calculateOptimalHourToPost());
        userResponse.setMonthlyReach(statisticsService.getUserReachThisMonth(user));

        return userResponse;
    }



    /**
     * Deletes the user with the given id from the database
     * @param id user id
     */
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User updateUserPassword(Long id, String oldPassword,String newPassword) {
        User user = userRepository.findById(id).orElse(null);

        if(user != null){
            //True if the current password matches the given string
            if(encoder.matches(oldPassword, user.getPassword())){
                user.setPassword(encoder.encode(newPassword));
                userRepository.save(user);
                log.info("Successfully changed the password!");
            } else{
                log.error("Passwords do not match");
                throw new IllegalStateException("Passwords do not match");
            }
        }
        return user;
    }

    @Transactional
    public User updateUsername(Long id, String newUsername,String password) {
        User user = userRepository.findById(id).orElse(null);

        if(user != null){
            //True if the current password matches the given string
            if(encoder.matches(password, user.getPassword())){
                user.setUsername(newUsername);
                userRepository.save(user);
                log.info("Successfully changed the password!");
            } else{
                log.error("Passwords do not match");
                throw new IllegalStateException("Passwords do not match");
            }
        }
        return user;
    }

    public Long getUserIdFromJWT(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(currentPrincipalName);

        return userDetails.getId();
    }
}
