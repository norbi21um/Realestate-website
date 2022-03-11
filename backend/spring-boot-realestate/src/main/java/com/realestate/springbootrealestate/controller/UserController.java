package com.realestate.springbootrealestate.controller;

import com.realestate.springbootrealestate.model.Property;
import com.realestate.springbootrealestate.model.User;
import com.realestate.springbootrealestate.service.UserService;
import lombok.AllArgsConstructor;
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
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public User findById(@RequestParam(name = "id") Long id) {
        return userService.getUserById(id);
    }

}
