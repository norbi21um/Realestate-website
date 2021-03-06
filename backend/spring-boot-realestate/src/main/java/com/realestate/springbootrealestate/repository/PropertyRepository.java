package com.realestate.springbootrealestate.repository;

import com.realestate.springbootrealestate.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Property JPA repository
 */
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    /**
     * Ez kell, majd a lekérdezéshez, hogy adott hírdetéshez mennyi clikkelés tartozik
    https://tedblob.com/spring-data-jpa-count-group-by/
    @Query(value = "SELECT c.customer_type, COUNT(c.customer_type) FROM Customer AS c GROUP BY c.customer_type")
    List<Object[]> countCustomersByType();*/

    List<Property> findAllByOrderByNumberOfClicksDesc();

    /**
     * Selects all the properties
     * returns them in descending order based on the price
     * @return list of properties
     */
    List<Property> findAllByOrderByPriceDesc();

    /**
     * Selects all the properties
     * returns them in ascending order based on the price
     * @return list of properties
     */
    List<Property> findAllByOrderByPriceAsc();

    /**
     * Select all the properties that are in the given district
     * @param district district
     * @return list of properties
     */
    List<Property> findByDistrict(String district);

    /**
     * Select all the properties where the address contains the given keyword
     * @param address address keyword
     * @return list of properties
     */
    List<Property> findByAddressContaining(String address);

    /**
     * Select all the properties where the address contains the given keyword
     * Returns them in ascending order based on the Price
     * @param address address keyword
     * @return list of properties
     */
    List<Property> findByAddressContainingOrderByPriceAsc(String address);

    /**
     * Select all the properties where the address contains the given keyword
     * Returns them in descending order based on the Price
     * @param address address keyword
     * @return list of properties
     */
    List<Property> findByAddressContainingOrderByPriceDesc(String address);

    List<Property> findByAddressContainingOrderByNumberOfClicksDesc(String address);

    /**
     * Select all the properties that are in the given district
     * and their addresses contain the address keyword
     * @param address address keyword
     * @param district district
     * @return list of properties
     */
    @Query("select p from Property p where p.address like ?1 and p.district = ?2")
    List<Property> findByDistrictAndAddress(String address, String district);

    /**
     * Select all the properties that are in the given district
     * and their addresses contain the address keyword
     * Returns them in descending order based on the price
     * @param address address keyword
     * @param district district
     * @return list of properties
     */
    @Query("select p from Property p where p.address like ?1 and p.district = ?2 order by p.price desc ")
    List<Property> findByDistrictAndAddressDesc(String address, String district);

    /**
     * Select all the properties that are in the given district
     * and their addresses contain the address keyword
     * Returns them in ascending order based on the price
     * @param address address keyword
     * @param district district
     * @return list of properties
     */
    @Query("select p from Property p where p.address like ?1 and p.district = ?2 order by p.price asc ")
    List<Property> findByDistrictAndAddressAsc(String address, String district);

    @Query("select p from Property p where p.address like ?1 and p.district = ?2 order by p.numberOfClicks desc ")
    List<Property> findByDistrictAndAddressPopularity(String address, String district);
}
