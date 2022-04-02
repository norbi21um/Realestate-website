package com.realestate.springbootrealestate.controller;

import com.realestate.springbootrealestate.dto.response.MessageResponse;
import com.realestate.springbootrealestate.dto.response.UserResponse;
import com.realestate.springbootrealestate.model.Property;
import com.realestate.springbootrealestate.model.User;
import com.realestate.springbootrealestate.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    //Csak tesztelés
    @GetMapping(value = "/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public UserResponse findById(@RequestParam(name = "id") Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping(value = "/deleteUser")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteUser(@RequestParam(name = "id") Long id) {
        String message = "";
        try {
            userService.deleteUserById(id);

            message = "Deletion complited";
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            message = "Could not delete user";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

}
