package com.systems.springframework.spring6restmvc.service;

import com.systems.springframework.spring6restmvc.model.Beer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public Beer getBeerByUuid(UUID id) {
        log.debug("Beer id in service was called "+ id);

        return Beer.builder()
                .id(id)
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle("Flex")
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHold(122)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }
}
