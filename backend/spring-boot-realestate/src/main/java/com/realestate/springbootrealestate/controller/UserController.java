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
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Recovers the user based on the security context,
     * then returns a UserResponse object build from the user.
     * @return UserResponse
     */
    @GetMapping(value = "/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public UserResponse findById() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(currentPrincipalName);

        return userService.getUserById(userDetails.getId());
    }

    /**
     * Recovers the user based on the security context,
     * then deletes the user and returns a message in the ResponseEnity body
     * @return ResponseEntity with MessageResponse
     */
    @DeleteMapping(value = "/deleteUser")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteUser() {
        String message = "";
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(currentPrincipalName);

            userService.deleteUserById(userDetails.getId());

            message = "Deletion complited";
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            message = "Could not delete user";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

    //http://localhost:8080/api/updateUser?oldaPassword=password1&newPassword=password
    //Ezzel az URL-el működik
    @PutMapping(value = "/updateUserPassword")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void updateUserPassword(@RequestParam(name = "oldaPassword") String oldaPassword,
                                   @RequestParam(name = "newPassword") String newPassword){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(currentPrincipalName);

        userService.updateUserPassword(userDetails.getId(), oldaPassword, newPassword);

    }

    //http://localhost:8080/api/updateUsename?newUsername=valami&password=password
    //Ezzel az URL-el működik
    //Miután megváltozik a username, már nem jó a mostani JWT token, így újra be kell jelentkezni
    @PutMapping(value = "/updateUsername")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public User updateUsename(@RequestParam(name = "newUsername") String newUsername,
                              @RequestParam(name = "password") String password){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(currentPrincipalName);

        return userService.updateUsername(userDetails.getId(), newUsername, password);

    }

}
