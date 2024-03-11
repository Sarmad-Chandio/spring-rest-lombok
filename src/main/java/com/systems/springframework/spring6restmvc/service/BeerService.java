package com.systems.springframework.spring6restmvc.service;

import com.systems.springframework.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<BeerDTO> beerList();

     Optional<BeerDTO> getBeerByUuid(UUID id);

     BeerDTO saveBeerObject(BeerDTO beer);

    void updateByUuid(UUID beerId, BeerDTO beer);

    void deleteByUuid(UUID beerId);

    void patchByUuid(UUID beerId, BeerDTO beer);
}
