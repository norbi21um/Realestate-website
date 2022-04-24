package com.realestate.springbootrealestate.controller;

import com.realestate.springbootrealestate.dto.request.PropertyRequest;
import com.realestate.springbootrealestate.dto.response.MessageResponse;
import com.realestate.springbootrealestate.model.Property;
import com.realestate.springbootrealestate.repository.PropertyRepository;
import com.realestate.springbootrealestate.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Porperty controller
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PropertyController {

    private final PropertyService propertyService;
    private final PropertyRepository propertyRepository;

    /**
     * Returns the property with matching id
     * @param id property id
     * @return Property
     */
    @GetMapping(value = "/{id}")
    public Property findById(@PathVariable("id") Long id) {
        return propertyService.getPropertyById(id);
    }

    /**
     * Deletes the requested property
     * Requires authorization
     * Accessible by USER, MODERATOR and ADMIN
     * @param id property id
     * @return message
     */
    @DeleteMapping(value = "/delete")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteProperty(@RequestParam(name = "id") Long id) {
        String message = "";
        try {
            propertyService.deletePropertyById(id);

            message = "Deletion complited";
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            message = "Could not delete property";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }


    /**
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

    /**
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


    @RequestMapping("")
    public Page<Property> getAllProperties(Pageable page) {
        /*Map<String, List<Property>> response = new HashMap<String, List<Property>>();
        if(sort.equals("asc")){
            response.put("properties", propertyService.getAllProperties(true, false, false));
        } else if(sort.equals("desc")){
            response.put("properties", propertyService.getAllProperties(false, true, false));
        } else if(sort.equals("popularity")){
            response.put("properties", propertyService.getAllProperties(false, false, true));
        } else {
            response.put("properties", propertyService.getAllProperties(false, false, false));
        }*/
        return propertyService.getAllProperties(page);

    }

    @RequestMapping("/pagination")
    public Page<Property> getAllValamiProperties(Pageable page) {

        return propertyService.getProperties(page);
    }

    /**
     * Post method for creating a new property
     * Requires authorization
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
