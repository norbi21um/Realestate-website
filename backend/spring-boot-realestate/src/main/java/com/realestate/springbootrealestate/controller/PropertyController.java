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

    @GetMapping(value = "/{id}")
    public Property findById(@PathVariable("id") Long id) {
        return propertyService.getPropertyById(id);
    }

    //@GetMapping(value = "/search")
    //public Map<String, List<Property>> findByDistrict(@RequestParam(name = "district") String district) {
    //    Map<String, List<Property>> response = new HashMap<String, List<Property>>();
    //    response.put("properties", propertyService.getPropertiesByDistrict(district));
    //    return response;
    //}
/**

    @GetMapping(value = "/searchByKeyword")
    public Map<String, List<Property>> findByAddress(@RequestParam(name = "address") String address) {
        Map<String, List<Property>> response = new HashMap<String, List<Property>>();
        response.put("properties", propertyService.getPropertiesByAddress(address));
        return response;
    }*/

    @GetMapping(value = "/searchByKeyword")
    public Map<String, List<Property>> findByAddress(@RequestParam(name = "district") String district,
                                                     @RequestParam(name = "address") String address) {
        Map<String, List<Property>> response = new HashMap<String, List<Property>>();
        response.put("properties", propertyService.getPropertiesByAddress(district, address));
        return response;
    }

    @GetMapping(value = "/recommendation")
    public Map<String, List<Property>> getrecommendation(@RequestParam(name = "district") String district){
        Map<String, List<Property>> response = new HashMap<String, List<Property>>();
        response.put("properties", propertyService.getRandomPropertiesByDistrict(district));
        return response;
    }

    @RequestMapping("")
    public Map<String, List<Property>> getAllProperties() {
        Map<String, List<Property>> response = new HashMap<String, List<Property>>();
        response.put("properties", propertyService.getAllProperties(false, false));
        return response;
    }

    @PostMapping("/createProperty")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Property createNewProperty(@RequestBody PropertyRequest propertyItem){
        return propertyService.createNewProperty(propertyItem);
    }

}
