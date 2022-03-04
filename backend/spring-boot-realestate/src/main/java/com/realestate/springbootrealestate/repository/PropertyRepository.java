package com.realestate.springbootrealestate.repository;

import com.realestate.springbootrealestate.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findByDistrictContaining(String district);
}
