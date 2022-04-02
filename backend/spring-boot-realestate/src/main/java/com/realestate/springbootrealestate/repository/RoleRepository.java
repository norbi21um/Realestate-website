package com.realestate.springbootrealestate.repository;

import com.realestate.springbootrealestate.model.ERole;
import com.realestate.springbootrealestate.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Role JPA repository
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a role by its name
     * Returns an Optional Role
     * @param name role name
     * @return Optional<Role>
     */
    Optional<Role> findByName(ERole name);
}