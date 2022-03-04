package com.realestate.springbootrealestate.repository;

import com.realestate.springbootrealestate.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    //Ehez ha hozzá írom, hogy Containing akkor az olyan, mintha SQL-ben a "like" kulcsszót használnám
    //findByDistrictContaining
    List<Property> findByDistrict(String district);

    List<Property> findByAddressContaining(String address);
}
