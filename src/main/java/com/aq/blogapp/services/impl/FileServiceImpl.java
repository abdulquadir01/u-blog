package com.aq.blogapp.services.impl;

import com.aq.blogapp.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {


    @Override
    public String uploadImg(String imagePath, MultipartFile imageFile) throws IOException {
//        file name
        String originalImageName = imageFile.getOriginalFilename();

//        random name generator
        String randomId = UUID.randomUUID().toString();

        String newImageName = randomId.concat(originalImageName.substring(Optional.of(originalImageName.lastIndexOf(".")).get()));

//        full path
        String filePath = imagePath + File.separator + newImageName;

//        Create folder if not created
        File f = new File(imagePath);
        if (!f.exists()) {
            f.mkdir();
        }

//        file copy from input-stream to a file
        Files.copy(imageFile.getInputStream(), Paths.get(filePath));

        return newImageName;
    }


    @Override
    public InputStream getBlogImage(String imagePath, String fileName) throws FileNotFoundException {

        String fullPath = imagePath + File.separator + fileName;

        return new FileInputStream(fullPath);
    }
}
