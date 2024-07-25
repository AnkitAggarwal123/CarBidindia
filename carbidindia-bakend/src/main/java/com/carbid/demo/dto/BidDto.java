package com.carbid.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BidDto {
    private Long carId;
    private BigDecimal amount;

}
