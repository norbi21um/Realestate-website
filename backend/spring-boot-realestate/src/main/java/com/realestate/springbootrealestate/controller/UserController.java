package com.realestate.springbootrealestate.controller;

import com.realestate.springbootrealestate.dto.response.MessageResponse;
import com.realestate.springbootrealestate.dto.response.UserResponse;
import com.realestate.springbootrealestate.model.Property;
import com.realestate.springbootrealestate.model.User;
import com.realestate.springbootrealestate.repository.UserRepository;
import com.realestate.springbootrealestate.service.UserDetailsImpl;
import com.realestate.springbootrealestate.service.UserDetailsServiceImpl;
import com.realestate.springbootrealestate.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User contoller
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public UserResponse findById() {
        return userService.getUserById(userService.getUserIdFromJWT());
    }


    @DeleteMapping(value = "/deleteUser")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteUser() {
        String message = "";
        try {
            userService.deleteUserById(userService.getUserIdFromJWT());

            message = "Deletion complited";
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            message = "Could not delete user";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

    @PutMapping(value = "/updateUserPassword")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public User updateUserPassword(@RequestParam(name = "oldaPassword") String oldaPassword,
                                   @RequestParam(name = "newPassword") String newPassword){
        return userService.updateUserPassword(userService.getUserIdFromJWT(), oldaPassword, newPassword);
    }


    @PutMapping(value = "/updateUsername")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public User updateUsename(@RequestParam(name = "newUsername") String newUsername,
                              @RequestParam(name = "password") String password){
        return userService.updateUsername(userService.getUserIdFromJWT(), newUsername, password);

    }
}
