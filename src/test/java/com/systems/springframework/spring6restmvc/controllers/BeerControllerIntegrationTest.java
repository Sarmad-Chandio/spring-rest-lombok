package com.systems.springframework.spring6restmvc.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.systems.springframework.spring6restmvc.entities.Beer;
import com.systems.springframework.spring6restmvc.exceptions.NotFoundException;
import com.systems.springframework.spring6restmvc.mappers.BeerMapper;
import com.systems.springframework.spring6restmvc.model.BeerDTO;
import com.systems.springframework.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
class BeerControllerIntegrationTest {
    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void seUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    @Test
    void testPatchBeerBadName() throws Exception{
        Beer beer=beerRepository.findAll().get(0);

        Map<String, Object> beerMap=new HashMap<>();
        beerMap.put("beerName","New Name 123654789123654789123654789123654789123654789123654789123654789123654789123654789123654789123654789123654789123654789123654789123654789123654789123654789123654789");

        MvcResult result=mockMvc.perform(patch("/api/v1/beer/"+beer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()",is(1)))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());


    }


    @Test
    void testListBeers(){
        List<BeerDTO> beerDTOS= beerController.beerList();
        assertThat(beerDTOS.size()).isEqualTo(2413);
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