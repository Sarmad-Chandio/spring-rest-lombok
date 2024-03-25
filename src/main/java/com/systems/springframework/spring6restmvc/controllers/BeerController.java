package com.systems.springframework.spring6restmvc.controllers;

import com.systems.springframework.spring6restmvc.exceptions.NotFoundException;
import com.systems.springframework.spring6restmvc.model.BeerDTO;
import com.systems.springframework.spring6restmvc.service.BeerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/beer")
public class BeerController {
    public static final String GET_BEER_PATH="/api/v1/beer";
    public  static final String GET_BEER_PATH_BY_ID=GET_BEER_PATH+"/{beerId}";
    private final BeerService beerService;
    Logger logger= LogManager.getLogger(BeerController.class);


    @RequestMapping(method = RequestMethod.GET)
    public List<BeerDTO> beerList(){
        logger.info("call ---> beerList()");
        return beerService.beerList();
    }
    @RequestMapping(value = "/{beerID}", method = RequestMethod.GET)
    public BeerDTO getBeerByUuid(@PathVariable("beerID") UUID beerID){
        logger.info("getBeerByUuid() inside getBeerByUuid() from controller ="+ beerID);
        return beerService.getBeerByUuid(beerID).orElseThrow(NotFoundException::new);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity saveBeerObject(@Validated @RequestBody BeerDTO beer){
        logger.info(" inside saveBeerObject() request object from controller ="+ beer);
        BeerDTO savedBeer=beerService.saveBeerObject(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/beer/json/"+savedBeer.getId().toString());

        return new ResponseEntity(headers,HttpStatus.CREATED);
    }
    @PutMapping("{beerId}")
    public ResponseEntity updateByUuid(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer){
        if (beerService.updateByUuid(beerId,beer).isEmpty()){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{beerId}")
    public ResponseEntity deleteByUuid(@PathVariable("beerId") UUID beerId){
        if ( ! beerService.deleteByUuid(beerId)){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{beerId}")
    public ResponseEntity patchByUuid(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer){
        beerService.patchByUuid(beerId,beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //Exceptions: if we are not using @ControllerAdvice : and if we want to costamize

    /*@ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(){
        return ResponseEntity.notFound().build();
    }*/


}
