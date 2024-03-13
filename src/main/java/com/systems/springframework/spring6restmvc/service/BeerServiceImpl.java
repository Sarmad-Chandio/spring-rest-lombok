package com.systems.springframework.spring6restmvc.service;

import com.systems.springframework.spring6restmvc.model.BeerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl(){
        this.beerMap=new HashMap<>();
        BeerDTO beer1 = BeerDTO.builder()
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

        BeerDTO beer2 = BeerDTO.builder()
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

        BeerDTO beer3 = BeerDTO.builder()
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
    public List<BeerDTO> beerList(){
        return new ArrayList<>(beerMap.values());
    }
    @Override
    public Optional<BeerDTO> getBeerByUuid(UUID id) {
        log.debug("Beer id in service was called "+ id);

        return Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDTO saveBeerObject(BeerDTO beer) {
        //mimicking DB layer implimentations
        BeerDTO savedBeer= BeerDTO.builder()
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
    public Optional<BeerDTO> updateByUuid(UUID beerId, BeerDTO beer) {
        //get existing data
        BeerDTO existingBeerObject=beerMap.get(beerId);
        //update existing
        existingBeerObject.setBeerName(beer.getBeerName());
        existingBeerObject.setBeerStyle(beer.getBeerStyle());
        existingBeerObject.setPrice(beer.getPrice());
        existingBeerObject.setUpc(beer.getUpc());
        existingBeerObject.setVersion(beer.getVersion());
        existingBeerObject.setQuantityOnHold(beer.getQuantityOnHold());

        beerMap.put(existingBeerObject.getId(),existingBeerObject);

        return Optional.of(existingBeerObject);

    }

    @Override
    public Boolean deleteByUuid(UUID beerId) {
        //deleting from MAP
        beerMap.remove(beerId);
        return true;

    }

    @Override
    public Optional<BeerDTO> patchByUuid(UUID beerId, BeerDTO beer) {
        //get existing data
        BeerDTO existingBeerObject=beerMap.get(beerId);

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

        return Optional.of(existingBeerObject);
    }
}
