package com.realestate.springbootrealestate.repository;

import com.realestate.springbootrealestate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User JPA repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Returns if the user exits with the given username
     * @param username username
     * @return boolean
     */
    Boolean existsByUsername(String username);

    /**
     * Returns if the user exits with the given email
     * @param email email
     * @return boolean
     */
    Boolean existsByEmail(String email);

    /**
     * Finds a user by its id
     * Returns an Optional User
     * @param id User id
     * @return Optional<User>
     */
    Optional<User> findById(Long id);

    /**
     * Finds a user by its username
     * Returns an Optional User
     * @param username username
     * @return Optional<User>
     */
    Optional<User> findByUsername(String username);
}