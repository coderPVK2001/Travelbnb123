package com.TravelPractise6.TravelPractise6.utils;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileConversionUtil {

    public static MultipartFile convertFileToMultipartFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream input = new FileInputStream(file);
        return new MockMultipartFile(file.getName(), file.getName(), "application/pdf", input);
    }
}
