package com.realestate.springbootrealestate.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PropertyRequest {

    private String address;

    private BigDecimal price;

    private BigDecimal area;

    private String imageUrl;

    private String description;

    private Long userId;

}