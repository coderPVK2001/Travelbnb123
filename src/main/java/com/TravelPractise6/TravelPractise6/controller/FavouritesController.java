package com.TravelPractise6.TravelPractise6.controller;

import com.TravelPractise6.TravelPractise6.entity.Favourites;
import com.TravelPractise6.TravelPractise6.entity.Property;
import com.TravelPractise6.TravelPractise6.entity.Userdetails;
import com.TravelPractise6.TravelPractise6.exception.PropertyIdNotFoundException;
import com.TravelPractise6.TravelPractise6.payload.FavouritesDto;
import com.TravelPractise6.TravelPractise6.repository.FavouritesRepository;
import com.TravelPractise6.TravelPractise6.repository.PropertyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/travelapi6/favourites")
public class FavouritesController {

    private FavouritesRepository favouritesRepository;
    private PropertyRepository propertyRepository;

    public FavouritesController(FavouritesRepository favouritesRepository, PropertyRepository propertyRepository) {
        this.favouritesRepository = favouritesRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addstatus(
            @AuthenticationPrincipal Userdetails userdetails,
            @RequestParam int propertyId,
            @RequestBody Favourites favourites
            ){
        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if(optionalProperty.isPresent()){
            Property property = optionalProperty.get();
            Optional<Favourites> optionalFavourites = favouritesRepository.findByUserAndProperty(userdetails, property);

            if(optionalFavourites.isPresent()){

                Favourites favourites1 = optionalFavourites.get();

                //if we removed the property from favourites list and now we again want to add it into favourites list
                if(favourites1.getStatus()==false){
                    favourites1.setStatus(favourites.getStatus());
                    Favourites savedFavourites1 = favouritesRepository.save(favourites1);
                    FavouritesDto favouritesDto1 = EntityToDto(savedFavourites1);
                    return new ResponseEntity<>(favouritesDto1, HttpStatus.CREATED);
                }
                else {
                    return new ResponseEntity<>("already added to favourites list" ,HttpStatus.BAD_REQUEST);
                }

            }

            favourites.setProperty(property);
            favourites.setUserdetails(userdetails);
            Favourites savedFavourites = favouritesRepository.save(favourites);
            FavouritesDto favouritesDto = EntityToDto(savedFavourites);
            return new ResponseEntity<>(favouritesDto, HttpStatus.CREATED);
        }
        else {
            throw new PropertyIdNotFoundException("property id is invalid");
        }
    }

    @GetMapping("/getallfavourites")
    public ResponseEntity<?> getallfavouriteslist(
            @AuthenticationPrincipal Userdetails userdetails
    ){
        List<Favourites> list = favouritesRepository.findByUser(userdetails);
        List<FavouritesDto> finalList = list.stream().map(x -> EntityToDto(x)).collect(Collectors.toList());
        return new ResponseEntity<>(finalList,HttpStatus.OK);
    }

    @DeleteMapping("/dislike")
    public ResponseEntity<?> deleteFavourites(@AuthenticationPrincipal Userdetails userdetails,
                                              @RequestParam int propertyId){

        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if(optionalProperty.isPresent()){
            Property property = optionalProperty.get();

            Optional<Favourites> optionalFavourites = favouritesRepository.findByUserAndProperty(userdetails, property);
            Favourites favourites = optionalFavourites.get();
            favourites.setStatus(false);
            Favourites savedFavourite = favouritesRepository.save(favourites);
            FavouritesDto favouritesDto = EntityToDto(savedFavourite);
            return new ResponseEntity<>(favouritesDto, HttpStatus.OK);
        }
        else {
            throw new PropertyIdNotFoundException("property id is invalid!!");
        }

    }

    private FavouritesDto EntityToDto(Favourites savedFavourites){
        FavouritesDto favouritesDto = new FavouritesDto();
        favouritesDto.setUsername(savedFavourites.getUserdetails().getUsername());
        favouritesDto.setPropertyname(savedFavourites.getProperty().getName());
        favouritesDto.setPricepernight(savedFavourites.getProperty().getPerNightPrice());
        favouritesDto.setStatus(savedFavourites.getStatus());
        favouritesDto.setCountryname(savedFavourites.getProperty().getCountry().getName());
        favouritesDto.setLocationname(savedFavourites.getProperty().getLocation().getName());

        return favouritesDto;
    }

}
