package com.systems.springframework.spring6restmvc.controllers;


import com.systems.springframework.spring6restmvc.entities.Beer;
import com.systems.springframework.spring6restmvc.exceptions.NotFoundException;
import com.systems.springframework.spring6restmvc.mappers.BeerMapper;
import com.systems.springframework.spring6restmvc.model.BeerDTO;
import com.systems.springframework.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class BeerControllerIntegrationTest {
    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Test
    void testListBeers(){
        List<BeerDTO> beerDTOS= beerController.beerList();
        assertThat(beerDTOS.size()).isEqualTo(3);
    }
    @Transactional
    @Rollback
    @Test
    void testSaveBeerObject(){
        BeerDTO beerDTO= BeerDTO.builder()
                .beerName("New Beer")
                .build();

        ResponseEntity responseEntity= beerController.saveBeerObject(beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String [] locationOfId=responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID =UUID.fromString(locationOfId[4]);

        Beer beer = beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();

    }
    @Test
    @Transactional
    @Rollback
    void testUpdateByUuid(){
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO=beerMapper.beerToBeerDto(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String beerName="UPDATED";
        beerDTO.setBeerName(beerName);

        ResponseEntity responseEntity=beerController.updateByUuid(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));

        Beer updatedBeer= beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);

    }
    @Test
    void updateByUuidNotFound(){
        assertThrows(NotFoundException.class, ()->{
            beerController.updateByUuid(UUID.randomUUID(),BeerDTO.builder().build());
        });
    }

    @Test
    void testGetBeerByUuid(){
        Beer beer= beerRepository.findAll().get(0);

        BeerDTO beerDTO= beerController.getBeerByUuid(beer.getId());
        assertThat(beerDTO).isNotNull();
    }
    @Test
    void testGetBeerByUuidNotFound(){
       assertThrows(NotFoundException.class, ()->{
           beerController.getBeerByUuid(UUID.randomUUID());
       });
    }
    @Test
    @Transactional
    @Rollback
    void testDeleteByUuid(){

        Beer beer= beerRepository.findAll().get(0);

        ResponseEntity responseEntity= beerController.deleteByUuid(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));
        assertThat(beerRepository.findById(beer.getId()).isEmpty());

        /*Beer beerFound=beerRepository.findById(beer.getId()).get();
        assertThat(beerFound).isNull();*/
    }
    @Test
    void testDeleteByUuidNotFound(){
        assertThrows(NotFoundException.class, ()->{
            beerController.deleteByUuid(UUID.randomUUID());
        });
    }

    @Test
    @Transactional
    @Rollback
    void testEmptyList(){
        beerRepository.deleteAll();
        List<BeerDTO> beerDTOS= beerController.beerList();
        assertThat(beerDTOS.size()).isEqualTo(0);
    }






}