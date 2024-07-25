package com.carbid.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonBidDto {

    private String carName;
    private String registrationNumber;
    private BigDecimal amount;
    private Date date;

}
