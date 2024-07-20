package com.TravelPractise6.TravelPractise6.controller;

import com.TravelPractise6.TravelPractise6.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/travelapi6/awsbucket")
public class BucketController {

    private BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @PostMapping(value = "/upload/{bucketname}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> upload(@RequestParam MultipartFile file,
                                    @PathVariable String bucketname){

        String url = bucketService.uploadFile(file, bucketname);
        return new ResponseEntity<>(url, HttpStatus.OK);
    }
}
