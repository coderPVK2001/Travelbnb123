package com.TravelPractise6.TravelPractise6.payload;

import lombok.Data;

@Data
public class PropertyDto {

    private String name;
    private Integer noOfBedrooms;
    private Integer noOfGuests;
    private Integer noOfBathrooms;
    private int perNightPrice;

    private int countryid;
    private int locationid;

}
