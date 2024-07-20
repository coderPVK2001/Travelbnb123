package com.TravelPractise6.TravelPractise6.payload;

import lombok.Data;

@Data
public class BookingsDto {

    private String guestname;
    private String email;
    private String mobile;
    private String propertyName;
    private String locationName;
    private String countryName;
    private int numberOfBedrooms;
    private int maxGuests;
    private int totalNights;
    private int perNightPrice;
    private int totalPrice;


}
