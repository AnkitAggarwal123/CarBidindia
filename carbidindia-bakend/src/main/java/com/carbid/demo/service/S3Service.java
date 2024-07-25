package com.carbid.demo.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.carbid.demo.model.Car;
import com.carbid.demo.model.CarImage;
import com.carbid.demo.repo.ICar;
import com.carbid.demo.repo.ICarImage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 client;
    @Autowired
    private ICarImage imageRepo;

    @Autowired
    ICar iCar;

    @Value("${BUCKET_NAME}")
    private String bucketName;

    @SneakyThrows
    public String ImageUploader(MultipartFile image, Long id){
        String orgName = image.getOriginalFilename();

        ObjectMetadata objectMetadata=new ObjectMetadata();
        objectMetadata.setContentLength(image.getSize());
        String toSave = UUID.randomUUID().toString() + orgName.substring(orgName.lastIndexOf("."));
        try {
            PutObjectResult putObjectResult = client.putObject(bucketName,toSave,image.getInputStream(),objectMetadata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Car car = iCar.findById(id).orElseThrow(()-> new RuntimeException("car not found with id" + id));

        CarImage carImage = new CarImage();
        carImage.setOriginalName(orgName);
        carImage.setSavedName(toSave);

        carImage.setCar(car);
        imageRepo.save(carImage);
        return "image uploaded successfully";
    }

    public String getUnSignedUrl(String imageId){
        Date expire=new Date();
        long time = expire.getTime();
        time=time+3*60*60*1000;
        expire.setTime(time);
        return client.generatePresignedUrl(bucketName,imageId, expire).toString();
    }

//    public List<ImageOutPutDto> getAllImages(){
//        List<ImageOutPutDto> imageOutPutDtos=new ArrayList<>();
//        List<Image> images=imageRepo.findAll();
//
//        for(var image : images){
//            imageOutPutDtos.add(new ImageOutPutDto(image.getId(),image.getOriginalName(),getUnSignedUrl(image.getSavedName())));
//        }
//        return imageOutPutDtos;
//    }    =

}