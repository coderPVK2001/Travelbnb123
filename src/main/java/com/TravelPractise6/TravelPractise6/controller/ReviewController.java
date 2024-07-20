package com.TravelPractise6.TravelPractise6.controller;

import com.TravelPractise6.TravelPractise6.entity.Property;
import com.TravelPractise6.TravelPractise6.entity.Reviews;
import com.TravelPractise6.TravelPractise6.entity.Userdetails;
import com.TravelPractise6.TravelPractise6.exception.PropertyIdNotFoundException;
import com.TravelPractise6.TravelPractise6.payload.ReviewDto;
import com.TravelPractise6.TravelPractise6.payload.ReviewDto2;
import com.TravelPractise6.TravelPractise6.payload.ReviewDto3;
import com.TravelPractise6.TravelPractise6.repository.PropertyRepository;
import com.TravelPractise6.TravelPractise6.repository.ReviewsRepository;
import com.TravelPractise6.TravelPractise6.repository.UserdetailsRepository;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/travel6/review")
public class ReviewController {

    private ReviewsRepository reviewsRepository;
    private PropertyRepository propertyRepository;
    private UserdetailsRepository userdetailsRepository;

    public ReviewController(ReviewsRepository reviewsRepository, PropertyRepository propertyRepository, UserdetailsRepository userdetailsRepository) {
        this.reviewsRepository = reviewsRepository;
        this.propertyRepository = propertyRepository;
        this.userdetailsRepository = userdetailsRepository;
    }

    //add new review
    @PostMapping("/add")
    public ResponseEntity<?> addreview(
            @AuthenticationPrincipal Userdetails userdetails,
            @Valid @RequestBody ReviewDto reviewDto,
            BindingResult bindingResult,
            @RequestParam int propertyId    ) {

        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        Optional<Reviews> optional = reviewsRepository.findByUseridAndPropertyId(userdetails.getId(), propertyId);
        if(optional.isPresent()){

            throw new PropertyIdNotFoundException("Already review is given for these property:");
        }

        Reviews reviews=new Reviews();
        reviews.setDescription(reviewDto.getDescription());
        reviews.setRating(reviewDto.getRating());
        reviews.setUserdetails(userdetails);

        Optional<Property> opt = propertyRepository.findById(propertyId);
        if(opt.isPresent()){

            Property property = opt.get();
            reviews.setProperty(property);
            Reviews savedReviews = reviewsRepository.save(reviews);          //**

            ReviewDto2 reviewDto2=new ReviewDto2();
            reviewDto2.setUsername(userdetails.getUsername());
            reviewDto2.setRating(savedReviews.getRating());
            reviewDto2.setDescription(savedReviews.getDescription());
            reviewDto2.setPropertyname(savedReviews.getProperty().getName());
            reviewDto2.setLocation(savedReviews.getProperty().getLocation().getName());
            reviewDto2.setCountry(savedReviews.getProperty().getCountry().getName());

            return new ResponseEntity<>(reviewDto2, HttpStatus.CREATED);
        }else{
            throw new PropertyIdNotFoundException("Given property id:"+propertyId +" not found!!");
        }
    }
//    .....................................................................................

    // get reviews by property name
    @GetMapping("/get")
    public ResponseEntity<?> getReviewsByProperty(
            @RequestParam String propertyName
    ){
        Optional<Property> optional=propertyRepository.findByName(propertyName);

        if(optional.isPresent()){

            Property property = optional.get();
            Integer propertyId = property.getId();

            List<Reviews> listReviews = reviewsRepository.findByPropertyName(propertyId);
            List<ReviewDto3> result = listReviews.stream().map(x -> EntityToDto(x)).collect(Collectors.toList());
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        return new ResponseEntity<>("nulla",HttpStatus.BAD_REQUEST);
    }

    public ReviewDto3 EntityToDto(Reviews reviews){

            ReviewDto3 reviewDto3 = new ReviewDto3();
            reviewDto3.setUsername(reviews.getUserdetails().getUsername());
            reviewDto3.setRating(reviews.getRating());
            reviewDto3.setDescription(reviews.getDescription());

            return reviewDto3;
    }
//.................................................................................................


    //find list of reviews by particular user
    @GetMapping("/reviewslist")
    public ResponseEntity<?> reviewslist(@AuthenticationPrincipal Userdetails userdetails){

        List<Reviews> reviews= reviewsRepository.findByUserId(userdetails);
        List<ReviewDto2> result = reviews.stream().map(x -> EntityToDto2(x)).collect(Collectors.toList());
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    public ReviewDto2 EntityToDto2(Reviews reviews){

        ReviewDto2 reviewDto2 = new ReviewDto2();
        reviewDto2.setUsername(reviews.getUserdetails().getUsername());
        reviewDto2.setPropertyname(reviews.getProperty().getName());
        reviewDto2.setRating(reviews.getRating());
        reviewDto2.setDescription(reviews.getDescription());
        reviewDto2.setCountry(reviews.getProperty().getCountry().getName());
        reviewDto2.setLocation(reviews.getProperty().getLocation().getName());

        return reviewDto2;
    }

//    .................................................

    //delete reviews for particular user with given propertyid
    @DeleteMapping("/deletereview")
    public ResponseEntity<?> deleteReview(
            @AuthenticationPrincipal Userdetails userdetails,
            @RequestParam int propertyId
    ) {
        Optional<Reviews> optional = reviewsRepository.findByUseridAndPropertyId(userdetails.getId(), propertyId);

        if(optional.isPresent()){
            Reviews reviews = optional.get();
            reviewsRepository.deleteById(reviews.getId());
            return new ResponseEntity<>("deleted successfully!!", HttpStatus.OK);

        }else {
            throw new PropertyIdNotFoundException("given property id:-"+propertyId+ " not found");
        }
    }


}
