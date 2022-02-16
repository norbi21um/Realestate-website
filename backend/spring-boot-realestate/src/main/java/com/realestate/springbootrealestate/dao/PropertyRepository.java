package com.realestate.springbootrealestate.dao;

import com.realestate.springbootrealestate.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PropertyRepository extends JpaRepository<Property, Long> {

}
