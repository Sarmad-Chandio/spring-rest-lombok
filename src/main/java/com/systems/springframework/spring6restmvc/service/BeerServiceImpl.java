package com.systems.springframework.spring6restmvc.service;

import com.systems.springframework.spring6restmvc.model.Beer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public void updateByUuid(UUID beerId, Beer beer) {
        //get existing data
        Beer existingBeerObject=beerMap.get(beerId);
        //update existing
        existingBeerObject.setBeerName(beer.getBeerName());
        existingBeerObject.setBeerStyle(beer.getBeerStyle());
        existingBeerObject.setPrice(beer.getPrice());
        existingBeerObject.setUpc(beer.getUpc());
        existingBeerObject.setVersion(beer.getVersion());
        existingBeerObject.setQuantityOnHold(beer.getQuantityOnHold());

        beerMap.put(existingBeerObject.getId(),existingBeerObject);

    }

    @Override
    public void deleteByUuid(UUID beerId) {
        //deleting from MAP
        beerMap.remove(beerId);

    }

    @Override
    public void patchByUuid(UUID beerId, Beer beer) {
        //get existing data
        Beer existingBeerObject=beerMap.get(beerId);

        if (StringUtils.hasText(beer.getBeerName())) {
            existingBeerObject.setBeerName(beer.getBeerName());
        }

        // Check and update beer style if not null
        if (beer.getBeerStyle() != null) {
            existingBeerObject.setBeerStyle(beer.getBeerStyle());
        }

        // Check and update price if not null
        if (beer.getPrice() != null) {
            existingBeerObject.setPrice(beer.getPrice());
        }

        // Check and update UPC if not emtpy
        if (StringUtils.hasText(beer.getUpc())) {
            existingBeerObject.setUpc(beer.getUpc());
        }

        // Check and update version if not null
        if (beer.getVersion() != null) {
            existingBeerObject.setVersion(beer.getVersion());
        }

        // Check and update quantity on hold if not null
        if (beer.getQuantityOnHold() != null) {
            existingBeerObject.setQuantityOnHold(beer.getQuantityOnHold());
        }

    }
}
