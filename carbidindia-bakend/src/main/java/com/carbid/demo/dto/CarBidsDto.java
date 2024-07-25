package com.carbid.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CarBidsDto {
    private Long carId;
    private String carName;
    private String registrationNumber;
    private List<BidDetailsDto> bids;
}