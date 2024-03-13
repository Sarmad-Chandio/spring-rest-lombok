package com.systems.springframework.spring6restmvc.repositories;

import com.systems.springframework.spring6restmvc.entities.Beer;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {

        Beer savedBeer= beerRepository.save(Beer.builder()
                .beerName("My beer")
                        .beerStyle("PALE_ALE")
                        .upc("12345")
                        .price(new BigDecimal("1358.8"))
                .build());
        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
    @Test
    void testSaveBeerTooLongName() {
        assertThrows(ConstraintViolationException.class, ()->{
            Beer savedBeer= beerRepository.save(Beer.builder()
                    .beerName("My beer LongName 12456987 12456987 12456987 12456987 12456987 12456987 12456987 12456987 12456987 12456987 12456987 12456987 12456987 12456987")
                    .beerStyle("PALE_ALE")
                    .upc("12345")
                    .price(new BigDecimal("1358.8"))
                    .build());
            beerRepository.flush();
        });
    }

}