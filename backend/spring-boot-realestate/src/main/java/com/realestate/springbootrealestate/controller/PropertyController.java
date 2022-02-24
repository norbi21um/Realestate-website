package com.realestate.springbootrealestate.controller;

import com.realestate.springbootrealestate.dto.PropertyItem;
import com.realestate.springbootrealestate.model.Property;
import com.realestate.springbootrealestate.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/properties")
@CrossOrigin(origins = "*")
public class PropertyController {

    private final PropertyService propertyService;

    @GetMapping(value = "/{id}")
    public Property findById(@PathVariable("id") Long id) {
        return propertyService.getPropertyById(id);
    }

    @RequestMapping("")
    public Map<String, List<Property>> getAllProperties() {
        Map<String, List<Property>> response = new HashMap<String, List<Property>>();
        response.put("properties", propertyService.getAllProperties());
        return response;
    }

    @PostMapping("/createProperty")
    @ResponseStatus(HttpStatus.CREATED)
    public Property createNewProperty(@RequestBody PropertyItem propertyItem){
        return propertyService.createNewProperty(propertyItem);
    }

}
