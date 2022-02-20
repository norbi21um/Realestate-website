package com.realestate.springbootrealestate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PropertyItem {

    private String address;

    private BigDecimal price;

    private BigDecimal area;

    private String imageUrl;

    private String description;

    private Long userId;

}