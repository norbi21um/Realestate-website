package com.realestate.springbootrealestate.service;

import com.realestate.springbootrealestate.dto.request.PropertyRequest;
import com.realestate.springbootrealestate.model.Property;
import com.realestate.springbootrealestate.model.User;
import com.realestate.springbootrealestate.repository.PropertyRepository;
import com.realestate.springbootrealestate.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public Property createNewProperty(PropertyRequest propertyItem) {
        Long userId = propertyItem.getUserId();

        User user = userRepository.findById(userId).orElse(null);

        if(user != null){
            Property property = new Property();

            property.setAddress(propertyItem.getAddress());
            property.setPrice(propertyItem.getPrice());
            property.setArea(propertyItem.getArea());
            property.setImageUrl(propertyItem.getImageUrl());
            property.setDescription(propertyItem.getDescription());
            property.setUser(user);
            property.setDistrict(propertyItem.getDistrict());

            propertyRepository.save(property);

        }

        return null;
    }

    public List<Property> getAllProperties(){
        return propertyRepository.findAll();
    }

    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Property not foudn with the id of: " + id));
    }

    //public List<Property> getPropertiesByDistrict(String district){
    //    return propertyRepository.findByDistrict(district);
    //}

    public List<Property> getPropertiesByAddress(String district, String address){

        if(district.equals("0")){
            return propertyRepository.findByAddressContaining(address);
        } else {
            String addressToUseInLikeOperation = "%" + address +"%";
            return propertyRepository.findByDistrictAndAddress(addressToUseInLikeOperation, district);
        }


    }

}
