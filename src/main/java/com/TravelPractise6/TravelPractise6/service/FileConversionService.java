package com.TravelPractise6.TravelPractise6.service;

import com.TravelPractise6.TravelPractise6.utils.FileConversionUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileConversionService {

    public MultipartFile getMultipartFileFromPath(String filePath) {
        try {
            MultipartFile multipartFile = FileConversionUtil.convertFileToMultipartFile(filePath);
            return multipartFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
