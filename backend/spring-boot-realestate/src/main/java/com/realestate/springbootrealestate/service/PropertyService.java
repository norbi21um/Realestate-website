package com.realestate.springbootrealestate.service;

import com.realestate.springbootrealestate.dto.request.PropertyRequest;
import com.realestate.springbootrealestate.model.Click;
import com.realestate.springbootrealestate.model.Property;
import com.realestate.springbootrealestate.model.User;
import com.realestate.springbootrealestate.repository.ClickRepository;
import com.realestate.springbootrealestate.repository.PropertyRepository;
import com.realestate.springbootrealestate.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Property service
 */
@Service
@Slf4j
@AllArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final ClickRepository clickRepository;


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
            property.setNumberOfClicks(0);

            propertyRepository.save(property);

            return property;
        }

        log.warn("No user found by the name of " + userId);
        return null;
    }


    public Page<Property> getAllProperties(Pageable page, String sortBy){
        List<Property> properties;

        if(sortBy.equals("desc")){
            properties = propertyRepository.findAllByOrderByPriceDesc();
        } else if(sortBy.equals("asc")){
            properties = propertyRepository.findAllByOrderByPriceAsc();
        } else if(sortBy.equals("popuparity")){
            properties = propertyRepository.findAllByOrderByNumberOfClicksDesc();
        } else {
            properties = propertyRepository.findAll();
        }

        int start = (int)page.getOffset();
        int end = Math.min((start + page.getPageSize()), properties.size());
        Page<Property> pagenatedList = new PageImpl<>(properties.subList(start, end), page, properties.size());
        return pagenatedList;
    }


    @Transactional
    public Property getPropertyById(Long id) {
        Property property = propertyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Property not foudn with the id of: " + id));

        /**Régi fajta amit majd törölni kell
         * Majd kéne csinálni egy új property DTO-t,ahol a numberOfClicks
         * A propertihez tartozó click sorok számától függene, akkor majd ki lehet ezt törölni
         * TODO: Ezt megcsinálni
         * **/
        int clicks = property.getNumberOfClicks();
        clicks++;
        property.setNumberOfClicks(clicks);

        Click click = new Click();
        click.setProperty(property);
        click.setUser(property.getUser());
        clickRepository.save(click);

        //property.getClicks().add(new Click(property));
        propertyRepository.save(property);
        return property;
    }



    public Page<Property> getSearchedProperties(Pageable page, String district, String address, String sortBy){
        List<Property> properties;
        if(district.equals("0")){ // district == 0 means, that the user did not select any district
            if(sortBy.equals("asc")){
                properties = propertyRepository.findByAddressContainingOrderByPriceAsc(address);
            } else if(sortBy.equals("desc")){
                properties = propertyRepository.findByAddressContainingOrderByPriceDesc(address);
            } else if(sortBy.equals("popularity")){
                properties = propertyRepository.findByAddressContainingOrderByNumberOfClicksDesc(address);
            } else {
                properties = propertyRepository.findByAddressContaining(address);
            }
        } else if(sortBy.equals("asc")){
            String addressToUseInLikeOperation = "%" + address +"%";
            properties = propertyRepository.findByDistrictAndAddressAsc(addressToUseInLikeOperation, district);
        } else if(sortBy.equals("desc")){
            String addressToUseInLikeOperation = "%" + address +"%";
            properties = propertyRepository.findByDistrictAndAddressDesc(addressToUseInLikeOperation, district);
        } else if(sortBy.equals("popularity")){
            String addressToUseInLikeOperation = "%" + address +"%";
            properties = propertyRepository.findByDistrictAndAddressPopularity(addressToUseInLikeOperation, district);
        } else {
            String addressToUseInLikeOperation = "%" + address +"%";
            properties = propertyRepository.findByDistrictAndAddress(addressToUseInLikeOperation, district);
        }
        int start = (int)page.getOffset();
        int end = Math.min((start + page.getPageSize()), properties.size());
        Page<Property> pagenatedList = new PageImpl<>(properties.subList(start, end), page, properties.size());
        return pagenatedList;
    }

    public List<Property> getRandomPropertiesByDistrict(String disctrict){
        return getRandomElements(propertyRepository.findByDistrict(disctrict), 4);
    }


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
