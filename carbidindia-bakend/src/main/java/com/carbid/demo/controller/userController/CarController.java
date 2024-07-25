package com.carbid.demo.controller.userController;

import com.carbid.demo.dto.BidDto;
import com.carbid.demo.dto.CarBidCountDTO;
import com.carbid.demo.dto.CarBidsDto;
import com.carbid.demo.dto.CarDto;
import com.carbid.demo.model.Car;
import com.carbid.demo.service.BidServices;
import com.carbid.demo.service.BidServicesWithoutPaper;
import com.carbid.demo.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("hasRole('USER')")
public class CarController {

    @Autowired
    CarService carService;

















}

