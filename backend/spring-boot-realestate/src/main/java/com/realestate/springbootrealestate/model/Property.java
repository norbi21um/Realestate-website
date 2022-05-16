package com.realestate.springbootrealestate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Property JPA Entity
 */
@Entity
@Table(name = "property")
@Data
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "district")
    private String district;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District districtTest;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "area")
    private BigDecimal area;

    @Column(name = "image_url")
    private String imageUrl;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**EZT MAJD TÖRÖLNI**/
    @Column(name = "number_of_clicks")
    private Integer numberOfClicks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "property")
    @JsonIgnore
    private Set<Click> clicks;
}
