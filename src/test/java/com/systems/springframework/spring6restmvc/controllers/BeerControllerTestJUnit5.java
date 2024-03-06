package com.systems.springframework.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systems.springframework.spring6restmvc.model.Beer;
import com.systems.springframework.spring6restmvc.service.BeerService;
import com.systems.springframework.spring6restmvc.service.BeerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@WebMvcTest(BeerController.class)
class BeerControllerTestJUnit5 {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl=new BeerServiceImpl();

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
    void getBeerByUuid() throws Exception {

        //ask mockito to return response data
       Beer beerTest=beerServiceImpl.beerList().get(0);

       given(beerService.getBeerByUuid(beerTest.getId())).willReturn(beerTest);


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
}