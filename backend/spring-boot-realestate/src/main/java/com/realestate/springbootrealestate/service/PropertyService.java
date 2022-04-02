package com.realestate.springbootrealestate.service;

import com.realestate.springbootrealestate.dto.request.PropertyRequest;
import com.realestate.springbootrealestate.model.Property;
import com.realestate.springbootrealestate.model.User;
import com.realestate.springbootrealestate.repository.PropertyRepository;
import com.realestate.springbootrealestate.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    /***
     * Finds the user, based on the id provided by the property item.
     * If the user exists, the function creates a new property and saves it to the database
     * if the user does not exist it return null.
     * @param propertyItem Property tranfer data object
     * @return null
     */
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

    /***
     * Returns every property from the database ether in an ascending order or a descending order
     * @param ascend Flag for ascending order
     * @param descend Flag for descending order
     * @return properties
     */
    public List<Property> getAllProperties(boolean ascend, boolean descend){
        if(ascend){
            return propertyRepository.findAllByOrderByPriceAsc();
        } else if(descend){
            return propertyRepository.findAllByOrderByPriceDesc();
        }
        return propertyRepository.findAll();
    }

    /***
     * Returns the property with matching id if it exits in the databse
     * else it throws an exception
     * @param id property id
     * @return property
     */
    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Property not foudn with the id of: " + id));
    }


    /***
     * Retrieves properties base on their districts, addresses and sorts them.
     * @param district district
     * @param address Keyword in the address
     * @param sortBy either ascending or descending order
     * @return properties
     */
    public List<Property> getSearchedProperties(String district, String address, String sortBy){
        if(district.equals("0")){
            if(sortBy.equals("asc")){
                return propertyRepository.findByAddressContainingOrderByPriceAsc(address);
            } else if(sortBy.equals("desc")){
                return propertyRepository.findByAddressContainingOrderByPriceDesc(address);
            } else {
                return propertyRepository.findByAddressContaining(address);
            }
        } else if(sortBy.equals("asc")){
            String addressToUseInLikeOperation = "%" + address +"%";
            return propertyRepository.findByDistrictAndAddressAsc(addressToUseInLikeOperation, district);
        } else if(sortBy.equals("desc")){
            String addressToUseInLikeOperation = "%" + address +"%";
            return propertyRepository.findByDistrictAndAddressDesc(addressToUseInLikeOperation, district);
        } else {
            String addressToUseInLikeOperation = "%" + address +"%";
            return propertyRepository.findByDistrictAndAddress(addressToUseInLikeOperation, district);
        }


    }

    /***
     * Creates suggestions based on the district of the viewed property
     * Retrieves all the properties with the same district as the view property
     * and selects 4 properties in a random order
     * @param disctrict district
     * @return properties
     */
    public List<Property> getRandomPropertiesByDistrict(String disctrict){
        return getRandomElements(propertyRepository.findByDistrict(disctrict), 4);
    }

    /***
     * Randomly selects "totalItems" numeber of properties for a list of properties
     * @param list list of properties
     * @param totalItems how many properties to choose
     * @return properties
     */
    private List<Property> getRandomElements(List<Property> list, int totalItems) {
        Random rand = new Random();

        //To avoid index out of bounds exception
        if(totalItems > list.size()){
            totalItems = list.size();
        }

        //temp list
        List<Property> newList = new ArrayList<>();
        for (int i = 0; i < totalItems; i++) {

            // random index number between 0 and list size
            int randomIndex = rand.nextInt(list.size());

            // Add to new list
            newList.add(list.get(randomIndex));

            // Remove selected element from original list
            list.remove(randomIndex);
        }
        return newList;
    }

    public void deletePropertyById(Long id) {
        propertyRepository.deleteById(id);
    }
}
