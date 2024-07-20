package com.TravelPractise6.TravelPractise6.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "image_url", length = 2000)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

}