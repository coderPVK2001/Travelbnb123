package com.TravelPractise6.TravelPractise6.payload;

import lombok.Data;

@Data
public class ReviewDto2 {

    private String username;
    private String propertyname;
    private double rating;
    private String description;
    private String location;
    private String country;
}
