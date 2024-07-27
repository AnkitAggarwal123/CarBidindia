package com.carbid.demo.controller.genric;

import com.carbid.demo.dto.CarDto;
import com.carbid.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class CarController_genric {

    @Autowired
    CarService carService;

    @GetMapping("car")
    public List<CarDto> allCar(){
        return carService.allCar();
    }

    @PutMapping("/visible/{value}/{id}")
    public String updateVisibility(@PathVariable boolean value, @PathVariable Long id){
        return carService.updateVisibility(value, id);

    }
}
