package com.TravelPractise6.TravelPractise6.payload;

import lombok.Data;

@Data
public class FavouritesDto {

    private String username;
    private String propertyname;
    private int pricepernight;
    private boolean status;
    private String locationname;
    private String countryname;

}
