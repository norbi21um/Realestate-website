package com.realestate.springbootrealestate.dao;

import com.realestate.springbootrealestate.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
public interface PropertyRepository extends JpaRepository<Property, Long> {

}
