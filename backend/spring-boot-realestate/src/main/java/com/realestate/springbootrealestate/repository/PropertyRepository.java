package com.realestate.springbootrealestate.repository;

import com.realestate.springbootrealestate.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    //Ehez ha hozzá írom, hogy Containing akkor az olyan, mintha SQL-ben a "like" kulcsszót használnám
    //findByDistrictContaining

    //Minden ingatlan ár szerint csökkenő sorrendben
    List<Property> findAllByOrderByPriceDesc();

    //Minden ingatlan ár szerint növekvő sorrendben
    List<Property> findAllByOrderByPriceAsc();

    List<Property> findByDistrict(String district);

    List<Property> findByAddressContaining(String address);

    @Query("select p from Property p where p.address like ?1 and p.district = ?2")
    List<Property> findByDistrictAndAddress(String address, String district);
}
