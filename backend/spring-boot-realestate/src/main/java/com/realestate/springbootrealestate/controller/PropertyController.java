package com.realestate.springbootrealestate.controller;

import com.realestate.springbootrealestate.dto.request.PropertyRequest;
import com.realestate.springbootrealestate.model.Property;
import com.realestate.springbootrealestate.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@AllArgsConstructor
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PropertyController {

    private final PropertyService propertyService;

    /***
     * Returns the property with matching id
     * @param id property id
     * @return Property
     */
    @GetMapping(value = "/{id}")
    public Property findById(@PathVariable("id") Long id) {
        return propertyService.getPropertyById(id);
    }


    /***
     * Returns the filtered list of properties the requested order
     * @param district district
     * @param address address keyword
     * @param sort either ascending or descending order
     * @return Properties inserted in a map
     */
    @GetMapping(value = "/searchByKeyword")
    public Map<String, List<Property>> findByAddress(@RequestParam(name = "district") String district,
                                                     @RequestParam(name = "address") String address,
                                                     @RequestParam(name = "sortBy") String sort) {
        Map<String, List<Property>> response = new HashMap<String, List<Property>>();
        response.put("properties", propertyService.getSearchedProperties(district, address, sort));
        return response;
    }

    /***
     * Get method that gives recommendations for the viewed property in the details page
     * @param district disctict
     * @return Properties inserted in a map
     */
    @GetMapping(value = "/recommendation")
    public Map<String, List<Property>> getrecommendation(@RequestParam(name = "district") String district){
        Map<String, List<Property>> response = new HashMap<String, List<Property>>();
        response.put("properties", propertyService.getRandomPropertiesByDistrict(district));
        return response;
    }

    /***
     * Get method for returning all the properties
     * @param sort either ascending or descending order
     * @return Properties inserted in a map
     */
    @RequestMapping("")
    public Map<String, List<Property>> getAllProperties(@RequestParam(name = "sortBy") String sort) {
        Map<String, List<Property>> response = new HashMap<String, List<Property>>();
        if(sort.equals("asc")){
            response.put("properties", propertyService.getAllProperties(true, false));
        } else if(sort.equals("desc")){
            response.put("properties", propertyService.getAllProperties(false, true));
        } else {
            response.put("properties", propertyService.getAllProperties(false, false));
        }
        return response;
    }

    /***
     * Post method for creating a new property
     * Required authorization
     * Accessible by USER, MODERATOR and ADMIN
     * @param propertyItem PropertyRequest, property dto
     * @return Property
     */
    @PostMapping("/createProperty")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Property createNewProperty(@RequestBody PropertyRequest propertyItem){
        return propertyService.createNewProperty(propertyItem);
    }

}
