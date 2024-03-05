package com.systems.springframework.spring6restmvc.service;

import com.systems.springframework.spring6restmvc.model.Beer;

import java.util.UUID;

public interface BeerService {
    public Beer getBeerByUuid(UUID id);
}
