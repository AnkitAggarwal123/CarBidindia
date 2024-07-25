package com.carbid.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarBidCountDTO {
    private Long carId;
    private String carName;
    private Integer bidCount;
}
