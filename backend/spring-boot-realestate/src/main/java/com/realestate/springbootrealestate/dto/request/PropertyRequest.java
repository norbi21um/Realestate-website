package com.realestate.springbootrealestate.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO for property creation
 */
@Getter
@Setter
@AllArgsConstructor
public class PropertyRequest {

    private String address;

    private String district;

    private BigDecimal price;

    private BigDecimal area;

    private String imageUrl;

    private String description;

    private Long userId;

}