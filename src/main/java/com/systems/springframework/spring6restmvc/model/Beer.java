package com.systems.springframework.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Builder
public class Beer {
    private UUID id;
    private Integer version;
    private String beerName;
    private String beerStyle;
    private String upc;
    private Integer quantityOnHold;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;


}
