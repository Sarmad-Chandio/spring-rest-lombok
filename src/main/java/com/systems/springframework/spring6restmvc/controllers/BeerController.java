package com.systems.springframework.spring6restmvc.controllers;

import com.systems.springframework.spring6restmvc.model.Beer;
import com.systems.springframework.spring6restmvc.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {
    private final BeerService beerService;
    public Beer getBeerByUuid(UUID id){
        log.debug("inside getBeerByUuid() from controller ="+ id);
        return beerService.getBeerByUuid(id);
    }
}
