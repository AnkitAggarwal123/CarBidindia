package com.carbid.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {


    private Long id;
    private String carName;
    private String registrationNumber;
    private Integer ownerNumber;
    private Integer modelNumber;
    private String fuelType;
    private String transmissionType;
    private String location;
    private String vehicleDetail;
    private boolean visible;
//    private String rcType;

//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime auctionEndTime;

    // Car images
    private List<String> imageUrls;
}
