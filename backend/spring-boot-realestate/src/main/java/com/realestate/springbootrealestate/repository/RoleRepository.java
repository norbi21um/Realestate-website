package com.realestate.springbootrealestate.repository;

import com.realestate.springbootrealestate.model.ERole;
import com.realestate.springbootrealestate.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}