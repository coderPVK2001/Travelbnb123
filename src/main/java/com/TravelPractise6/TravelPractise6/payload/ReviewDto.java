package com.TravelPractise6.TravelPractise6.payload;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class ReviewDto {

    private String description;

    @Range(min = 0, max = 5, message = "Rating should be withing 0-5 only!!")
    private double rating;

}
