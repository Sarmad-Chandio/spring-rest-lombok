package com.systems.springframework.spring6restmvc.service;

import com.systems.springframework.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<BeerDTO> beerList();

     Optional<BeerDTO> getBeerByUuid(UUID id);

     BeerDTO saveBeerObject(BeerDTO beer);

    Optional<BeerDTO> updateByUuid(UUID beerId, BeerDTO beer);

    Boolean deleteByUuid(UUID beerId);

    Optional<BeerDTO> patchByUuid(UUID beerId, BeerDTO beer);
}
