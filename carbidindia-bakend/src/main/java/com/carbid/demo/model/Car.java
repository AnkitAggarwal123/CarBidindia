package com.carbid.demo.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String carName;
    private String registrationNumber;
    private Integer ownerNumber;
    private Integer modelNumber;
    private String fuelType;
    private String transmissionType;
    private String location;
    private String vehicleDetail;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarImage> carImages;
    private boolean visible;
    private String rcType;
    private LocalDateTime auctionEndTime;



}
