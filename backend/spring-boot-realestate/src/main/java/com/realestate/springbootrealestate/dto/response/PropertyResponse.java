package com.realestate.springbootrealestate.dto.response;

import com.realestate.springbootrealestate.model.District;
import com.realestate.springbootrealestate.model.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PropertyResponse {

    private Long id;

    private String address;

    private String district;

    private District districtTest;

    private BigDecimal price;

    private BigDecimal area;

    private String imageUrl;

    private String description;

    private Date dateCreated;

    private User user;

    private Integer numberOfClicks;

}
