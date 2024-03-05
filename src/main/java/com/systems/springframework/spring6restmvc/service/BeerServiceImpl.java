package com.systems.springframework.spring6restmvc.service;

import com.systems.springframework.spring6restmvc.model.Beer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, Beer> beerMap;

    public BeerServiceImpl(){
        this.beerMap=new HashMap<>();
        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle("Flex")
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHold(122)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Chao Cat")
                .beerStyle("Flex-Speacial")
                .upc("1235651 ")
                .price(new BigDecimal("12.991"))
                .quantityOnHold(122)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Chao Marinee")
                .beerStyle("All Shapes")
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHold(122)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }

    @Override
    public List<Beer> beerList(){
        return new ArrayList<>(beerMap.values());
    }
    @Override
    public Beer getBeerByUuid(UUID id) {
        log.debug("Beer id in service was called "+ id);

        return beerMap.get(id);
    }

    @Override
    public Beer saveBeerObject(Beer beer) {
        //mimicking DB layer implimentations
        Beer savedBeer= Beer.builder()
                .id(UUID.randomUUID())
                .beerName(beer.getBeerName())
                .createdDate(LocalDateTime.now())
                .upc(beer.getUpc())
                .version(beer.getVersion())
                .price(beer.getPrice())
                .beerStyle(beer.getBeerStyle())
                .updatedDate(LocalDateTime.now())
                .quantityOnHold(beer.getQuantityOnHold())
                .build();

        beerMap.put(savedBeer.getId(),savedBeer);
        return savedBeer;
    }
}
