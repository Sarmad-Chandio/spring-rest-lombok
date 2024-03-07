package com.systems.springframework.spring6restmvc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systems.springframework.spring6restmvc.exceptions.NotFoundException;
import com.systems.springframework.spring6restmvc.model.Beer;
import com.systems.springframework.spring6restmvc.service.BeerService;
import com.systems.springframework.spring6restmvc.service.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@WebMvcTest(BeerController.class)
class BeerControllerTestJUnit5 {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;//Configuration Done By Spring Boot i-e: Formating data in our scenerio

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;
    @Captor
    ArgumentCaptor<Beer> beerArgumentCaptor;

    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    @BeforeEach
    void setUp() {
        beerServiceImpl=new BeerServiceImpl();
        uuidArgumentCaptor= ArgumentCaptor.forClass(UUID.class);
    }

    //test case for beerList
    @Test
    void beerList() throws Exception {
        List<Beer> beerList=beerServiceImpl.beerList();

        given(beerService.beerList()).willReturn(beerList);

        mockMvc.perform(get("/api/v1/beer"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)));

    }
    @Test
    void testDeleteBeer() throws Exception{
        Beer beer=beerServiceImpl.beerList().get(0);

        mockMvc.perform(delete("/api/v1/beer/"+ beer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteByUuid(uuidArgumentCaptor.capture());

        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }
    @Test
    void testPatchBeer() throws Exception{
        Beer beer=beerServiceImpl.beerList().get(0);

        Map<String, Object> beerMap=new HashMap<>();
        beerMap.put("beerName","New Name");

        mockMvc.perform(patch("/api/v1/beer/"+beer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).patchByUuid(uuidArgumentCaptor.capture(),beerArgumentCaptor.capture());

        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());

    }


    @Test
    void testUpdateByUuid() throws Exception{
        Beer beer= beerServiceImpl.beerList().get(0);

        mockMvc.perform(put("/api/v1/beer/"+ beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());
        // we can also use ArgumentCaptor for "any" argument

        verify(beerService).updateByUuid(any(UUID.class), any(Beer.class));
    }

    @Test
    void testCreatedNewBeer() throws Exception {
        /*
        ObjectMapper objectMapper= new ObjectMapper();
        // Jackson has modules, date and time will be handles by jackson lib
        // object Mapper could be provided by Spring Framework
        objectMapper.findAndRegisterModules();
       */
        Beer beer=beerServiceImpl.beerList().get(0);
        //beer object should not have version as well as id
        beer.setVersion(null);
        beer.setId(null);

        // perform mockito : returning 1st element form beeList method and passing to sAve beer service
        given(beerService.saveBeerObject(any(Beer.class))).willReturn(beerServiceImpl.beerList().get(1));

        //perform mocking with the help of mvc
        mockMvc.perform(post("/api/v1/beer") //action POST
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer))) //getting data from response object with the help of object mapper
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));


//        System.out.println(objectMapper.writeValueAsString(beer));
    }


    @Test
    void getBeerByUuid() throws Exception {

        //ask mockito to return response data
       Beer beerTest=beerServiceImpl.beerList().get(0);

       given(beerService.getBeerByUuid(beerTest.getId())).willReturn(Optional.of(beerTest));//return optional : it could be mull or data


        mockMvc.perform(get("/api/v1/beer/"+ beerTest.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //Response should be returned from mockito
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                // (JSON path "$.id") is taken from mock response and is compared with id of beerTest object
                .andExpect(jsonPath("$.id", is(beerTest.getId().toString())))
                // (JSON path "$.beerName")  is taken from mock response and is compared with BeerName of object
                .andExpect(jsonPath("$.beerName", is(beerTest.getBeerName())));

    }
    //Throw Exception using mockito
    @Test
    void getBeerByUuidNotFoundException() throws Exception {

        given(beerService.getBeerByUuid(any(UUID.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get(BeerController.GET_BEER_PATH_BY_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

}