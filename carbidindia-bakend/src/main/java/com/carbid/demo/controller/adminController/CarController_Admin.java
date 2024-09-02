package com.carbid.demo.controller.adminController;

import com.carbid.demo.controller.userController.CarController;
import com.carbid.demo.dto.CarDto;
import com.carbid.demo.model.Car;
import com.carbid.demo.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class CarController_Admin {

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    @Autowired
    CarService carService;

    @GetMapping("admin")
    public String check(){
        return "admin";
    }



    @PostMapping("car")
    public ResponseEntity<?> addCar(@RequestBody CarDto carDto) {
        try {
            Car car = carService.addCar(carDto);
            return ResponseEntity.ok(car);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding car");
        }
    }

    @PostMapping("add/car/image")
    public String uploadImages(@RequestParam("files")List<MultipartFile> multipartFiles, @RequestParam("id") Long id){
       return carService.uploadImage(multipartFiles, id);
    }


    @PutMapping("visible/admin/{value}/{id}")
    public String updateVisibility(@PathVariable boolean value, @PathVariable Long id){
        return carService.updateVisibility(value, id);

    }

    @PutMapping("auction/time/{carId}")
    public ResponseEntity<?> updateAuctionTime(@PathVariable Long carId, @RequestParam LocalDateTime localDateTime){
        String message =  carService.updateAuctionTime(carId, localDateTime);
       return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
