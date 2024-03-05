package com.systems.springframework.spring6restmvc.controllers;

import com.systems.springframework.spring6restmvc.model.Beer;
import com.systems.springframework.spring6restmvc.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/beer")
public class BeerController {
    private final BeerService beerService;


    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> beerList(){
        return beerService.beerList();
    }
    @RequestMapping(value = "/{beerID}", method = RequestMethod.GET)
    public Beer getBeerByUuid(@PathVariable("beerID") UUID beerID){
        log.debug("inside getBeerByUuid() from controller ="+ beerID);
        return beerService.getBeerByUuid(beerID);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity saveBeerObject(@RequestBody Beer beer){
        beerService.saveBeerObject(beer);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
