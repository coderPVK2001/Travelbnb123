package com.TravelPractise6.TravelPractise6.controller;

import com.TravelPractise6.TravelPractise6.entity.Images;
import com.TravelPractise6.TravelPractise6.entity.Property;
import com.TravelPractise6.TravelPractise6.exception.PropertyIdNotFoundException;
import com.TravelPractise6.TravelPractise6.repository.ImagesRepository;
import com.TravelPractise6.TravelPractise6.repository.PropertyRepository;
import com.TravelPractise6.TravelPractise6.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.Optional;

@RestController
@RequestMapping("/travel6/v1/image")
public class ImageController {

    private BucketService bucketService;
    private PropertyRepository propertyRepository;
    private ImagesRepository imagesRepository;

    public ImageController(BucketService bucketService, PropertyRepository propertyRepository, ImagesRepository imagesRepository) {
        this.bucketService = bucketService;
        this.propertyRepository = propertyRepository;
        this.imagesRepository = imagesRepository;
    }

    @PostMapping(value = "/upload/{bucketname}/property", consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> upload(@RequestParam MultipartFile file,
                                    @PathVariable String bucketname,
                                    @RequestParam int propertyId){
        System.out.println(propertyId);
        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);

        if(optionalProperty.isPresent()){
            Property property = optionalProperty.get();

            String url = bucketService.uploadFile(file, bucketname);

            Images images=new Images();
            images.setProperty(property);
            images.setImageUrl(url);
            Images savedImages = imagesRepository.save(images);

            return new ResponseEntity<>(savedImages, HttpStatus.OK);
        }
        else {
            throw new PropertyIdNotFoundException("property id "+propertyId +" is invalid!!");
        }
    }
}
