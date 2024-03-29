package com.systems.springframework.spring6restmvc.service;

import com.systems.springframework.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    List<Beer> beerList();

    public Beer getBeerByUuid(UUID id);

    public Beer saveBeerObject(Beer beer);

    void updateByUuid(UUID beerId, Beer beer);

    void deleteByUuid(UUID beerId);

    void patchByUuid(UUID beerId, Beer beer);
}
