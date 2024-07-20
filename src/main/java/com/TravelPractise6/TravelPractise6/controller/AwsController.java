package com.TravelPractise6.TravelPractise6.controller;

import com.amazonaws.Response;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/travel6/aws")
public class AwsController {
    
    private AmazonS3 amazonS3;

    public AwsController(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }
    
    @GetMapping("/list")
    public ResponseEntity<?> getall(){

        List<Bucket> buckets = amazonS3.listBuckets();

        return new ResponseEntity<>(buckets, HttpStatus.OK);
    }
    
}
