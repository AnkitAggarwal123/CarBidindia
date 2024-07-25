package com.carbid.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class BidDetailsDto {
    private Long id;
    private BigDecimal amount;
    private String bidder;
    private LocalDateTime date;
}
