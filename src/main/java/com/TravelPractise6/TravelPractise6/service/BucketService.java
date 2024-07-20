package com.TravelPractise6.TravelPractise6.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class BucketService {

    @Autowired
    private AmazonS3 amazonS3;

    public String uploadFile(MultipartFile file, String bucketName){
        if (file.isEmpty()){
            throw new IllegalStateException("cannot upload empty file");
        }
        try {
            File convFile=new File(System.getProperty("java.io.tmpdir")+ "/" + file.getOriginalFilename()); //get the path of file
            file.transferTo(convFile);             //converts file into binary
            try {
                amazonS3.putObject(bucketName,convFile.getName(),convFile);    // into bucket, file name will be given by .getName()
                                                                               //will upload the file
                return amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString();
            }catch (AmazonS3Exception s3Exception){
                return "unable to upload file :" +s3Exception.getMessage();
            }
        }catch (Exception e){
            throw new IllegalStateException("failed to upload file",e);
        }
    }
}
