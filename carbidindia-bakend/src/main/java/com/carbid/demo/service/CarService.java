package com.carbid.demo.service;

import com.carbid.demo.dto.CarDto;
import com.carbid.demo.model.Car;
import com.carbid.demo.model.CarImage;
import com.carbid.demo.repo.ICar;
import com.carbid.demo.repo.ICarImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private static final Logger logger = LoggerFactory.getLogger(CarService.class);


    @Autowired
    ICar carRepo;

    @Autowired
    ICarImage imageRepo;

    @Autowired
    private S3Service s3Service;

    public Car addCar(CarDto carDto) {
        // Check if a car with the same registration number already exists
        Optional<Car> existingCar = carRepo.findByRegistrationNumber(carDto.getRegistrationNumber());
        if (existingCar.isPresent()) {
            throw new IllegalArgumentException("Car with registration number " + carDto.getRegistrationNumber() + " already exists");
        }

        Car car = new Car();
        car.setCarName(carDto.getCarName());
        car.setFuelType(carDto.getFuelType());
        car.setLocation(carDto.getLocation());
        car.setAuctionEndTime(carDto.getAuctionEndTime());
        car.setVisible(carDto.isVisible());
        car.setModelNumber(carDto.getModelNumber());
        car.setOwnerNumber(carDto.getOwnerNumber());
        car.setTransmissionType(carDto.getTransmissionType());
        car.setVehicleDetail(carDto.getVehicleDetail());
        car.setRegistrationNumber(carDto.getRegistrationNumber());
        return carRepo.save(car);
    }

    public String uploadImage(List<MultipartFile> files, Long id){

        for (MultipartFile file : files){
            s3Service.ImageUploader(file, id);
        }
        return "Images uploaded successfully";
    }




    public List<CarDto> allCar() {

        List<Car> cars = carRepo.findAll();

        List<CarDto> carDtos = cars.stream()
                .map(car -> {
                    CarDto carDto = new CarDto();
                    carDto.setCarName(car.getCarName());
                    carDto.setVisible(car.isVisible());
                    carDto.setLocation(car.getLocation());
                    carDto.setFuelType(car.getFuelType());
                    carDto.setModelNumber(car.getModelNumber());
                    carDto.setOwnerNumber(car.getOwnerNumber());
                    carDto.setRegistrationNumber(car.getRegistrationNumber());
                    carDto.setAuctionEndTime(car.getAuctionEndTime());
                    carDto.setVehicleDetail(car.getVehicleDetail());
                    carDto.setTransmissionType(car.getTransmissionType());
                    carDto.setId(car.getId());

                    List<String> imageUrls = car.getCarImages().stream()
                            .map(carImage -> s3Service.getUnSignedUrl(carImage.getSavedName()))
                            .collect(Collectors.toList());
                    carDto.setImageUrls(imageUrls);
                    return carDto;
                }).collect(Collectors.toList());

        return carDtos;



    }

    public String updateVisibility(boolean value, Long id) {
        Car car = carRepo.findById(id).orElseThrow();
        car.setVisible(value);
        carRepo.save(car);
        return "update successfully";
    }

    public String updateAuctionTime(Long carId, LocalDateTime localDateTime) {

        Car car = carRepo.findById(carId).orElseThrow();
        car.setAuctionEndTime(localDateTime);
        carRepo.save(car);
        return "car AuctionEnd Time updated";
    }
}
