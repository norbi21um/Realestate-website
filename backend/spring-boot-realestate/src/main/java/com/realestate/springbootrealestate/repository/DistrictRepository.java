package com.realestate.springbootrealestate.repository;

import com.realestate.springbootrealestate.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public interface DistrictRepository extends JpaRepository<District, Long> {
}
