package com.realestate.springbootrealestate.controller;

import com.realestate.springbootrealestate.dto.request.PropertyRequest;
import com.realestate.springbootrealestate.dto.response.MessageResponse;
import com.realestate.springbootrealestate.dto.response.PropertyResponse;
import com.realestate.springbootrealestate.model.Property;
import com.realestate.springbootrealestate.repository.PropertyRepository;
import com.realestate.springbootrealestate.service.PropertyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PropertyController {

    private final PropertyService propertyService;


    @GetMapping(value = "/{id}")
    public PropertyResponse findById(@PathVariable("id") Long id) {
        return propertyService.getPropertyById(id);
    }


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



    @GetMapping(value = "/searchByKeyword")
    public Page<PropertyResponse>  findByAddress(Pageable page,
                                                     @RequestParam(name = "district") String district,
                                                     @RequestParam(name = "address") String address,
                                                     @RequestParam(name = "sortBy") String sort) {
        return propertyService.getSearchedProperties(page,district, address, sort);
    }


    @GetMapping(value = "/recommendation")
    public Map<String, List<PropertyResponse>> getrecommendation(@RequestParam(name = "district") String district){
        Map<String, List<PropertyResponse>> response = new HashMap<String, List<PropertyResponse>>();
        response.put("properties", propertyService.getRandomPropertiesByDistrict(district));
        return response;
    }


    @RequestMapping("")
    public Page<PropertyResponse> getAllProperties(Pageable page, String sortBy) {
        return propertyService.getAllProperties(page, sortBy);

    }


    @PostMapping("/createProperty")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Property createNewProperty(@RequestBody PropertyRequest propertyItem){
        return propertyService.createNewProperty(propertyItem);
    }

}
