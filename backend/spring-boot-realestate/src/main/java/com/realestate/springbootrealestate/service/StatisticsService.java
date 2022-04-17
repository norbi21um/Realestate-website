package com.realestate.springbootrealestate.service;

import com.realestate.springbootrealestate.repository.PropertyRepository;
import com.realestate.springbootrealestate.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.PropertyResourceBundle;

/**
 * Statistics service
 */
@Service
@AllArgsConstructor
public class StatisticsService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

}
