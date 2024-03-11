package com.systems.springframework.spring6restmvc.controllers;

import com.systems.springframework.spring6restmvc.model.BeerDTO;
import com.systems.springframework.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

@SpringBootTest
class BeerControllerIntegrationTest {
    @Autowired
    BeerController BeerController;
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testListBeers(){
        List<BeerDTO> beerDTOS= BeerController.beerList();
        assertThat(beerDTOS.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    void testEmptyList(){
        beerRepository.deleteAll();
        List<BeerDTO> beerDTOS= BeerController.beerList();
        assertThat(beerDTOS.size()).isEqualTo(0);
    }






}