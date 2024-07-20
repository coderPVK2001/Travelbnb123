package com.TravelPractise6.TravelPractise6.controller;

import com.TravelPractise6.TravelPractise6.entity.Country;
import com.TravelPractise6.TravelPractise6.entity.Location;
import com.TravelPractise6.TravelPractise6.entity.Property;
import com.TravelPractise6.TravelPractise6.entity.Reviews;
import com.TravelPractise6.TravelPractise6.exception.PropertyIdNotFoundException;
import com.TravelPractise6.TravelPractise6.payload.PropertyDto;
import com.TravelPractise6.TravelPractise6.payload.PropertyDto2;
import com.TravelPractise6.TravelPractise6.payload.PropertyDto3;
import com.TravelPractise6.TravelPractise6.repository.CountryRepository;
import com.TravelPractise6.TravelPractise6.repository.LocationRepository;
import com.TravelPractise6.TravelPractise6.repository.PropertyRepository;
import com.TravelPractise6.TravelPractise6.repository.ReviewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/travel6/prop")
public class PropertyController {

    private PropertyRepository propertyRepository;
    private CountryRepository countryRepository;
    private LocationRepository locationRepository;
    private ReviewsRepository reviewsRepository;

    public PropertyController(PropertyRepository propertyRepository, CountryRepository countryRepository, LocationRepository locationRepository, ReviewsRepository reviewsRepository) {
        this.propertyRepository = propertyRepository;
        this.countryRepository = countryRepository;
        this.locationRepository = locationRepository;
        this.reviewsRepository = reviewsRepository;
    }

    @PostMapping("/addproperty")
    public ResponseEntity<?> add(
            @RequestBody PropertyDto pdto
            ){

        Property property=new Property();
        property.setName(pdto.getName());
        property.setNoOfGuests(pdto.getNoOfGuests());
        property.setNoOfBedrooms(pdto.getNoOfBedrooms());
        property.setNoOfBathrooms(pdto.getNoOfBathrooms());
        property.setPerNightPrice(pdto.getPerNightPrice());

        Country country = countryRepository.findById(pdto.getCountryid()).get();
        Location location = locationRepository.findById(pdto.getLocationid()).get();

        property.setCountry(country);
        property.setLocation(location);

        Property savedProperty = propertyRepository.save(property);
        return new ResponseEntity<>(savedProperty, HttpStatus.CREATED);
    }

    @GetMapping("/searchproperty")
    public ResponseEntity<?> searchProperties(
            @RequestParam String name,
            @RequestParam String sort
    ) {

        List<Property> propertyList = propertyRepository.searchHotel(name);

        if (sort.equalsIgnoreCase("asc")) {
            List<Property> streamList = propertyList.stream().sorted(Comparator.comparingInt(x -> x.getPerNightPrice())).collect(Collectors.toList());
            List<PropertyDto2> result = streamList.stream().map(x -> EntityToDto(x)).collect(Collectors.toList());
            return new ResponseEntity<>(result, HttpStatus.OK);

        } else if (sort.equalsIgnoreCase("desc")) {
            List<Property> streamList = propertyList.stream().sorted(Comparator.comparingInt(Property::getPerNightPrice).reversed()).collect(Collectors.toList());
            List<PropertyDto2> result = streamList.stream().map(x -> EntityToDto(x)).collect(Collectors.toList());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return null;
    }


    private PropertyDto2 EntityToDto( Property property){

        PropertyDto2 pdto=new PropertyDto2();
        pdto.setName(property.getName());
        pdto.setPerNightPrice(property.getPerNightPrice());
        pdto.setNoOfGuests(property.getNoOfGuests());
        pdto.setNoOfBedrooms(property.getNoOfBedrooms());
        pdto.setNoOfBathrooms(property.getNoOfBathrooms());

        Country country = countryRepository.findById(property.getCountry().getId()).get();
        Location location = locationRepository.findById(property.getLocation().getId()).get();
        pdto.setCountryname(country.getName());
        pdto.setLocationname(location.getName());

        return pdto;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProperty( @RequestParam int propertyId){
        List<Reviews> propertyReviews = reviewsRepository.findByPropertyName(propertyId);
        reviewsRepository.deleteAll(propertyReviews);
//        for(Reviews r:propertyReviews){
//            reviewsRepository.deleteById(r.getId());
//        }

        propertyRepository.deleteById(propertyId);
        return new ResponseEntity<>("deleted successfully !!",HttpStatus.OK);
    }

// update details
    @PutMapping("/udpate")
    public ResponseEntity<?> updateProperty( @RequestParam int propertyId,
                                             @RequestBody PropertyDto3 propertyDto3){

        Optional<Property> opt = propertyRepository.findById(propertyId);
        if(opt.isPresent()){

            Property property = opt.get();
            property.setNoOfGuests(propertyDto3.getNoOfGuests());
            property.setPerNightPrice(propertyDto3.getPerNightPrice());
            Property savedProperty = propertyRepository.save(property);

            PropertyDto2 propertyDto2 = EntityToDto(savedProperty);
            return new ResponseEntity<>(propertyDto2, HttpStatus.OK);
        }
        else{
            throw new PropertyIdNotFoundException("property id is invalid!!");
        }
    }


    }
