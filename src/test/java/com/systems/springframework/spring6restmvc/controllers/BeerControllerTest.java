package com.systems.springframework.spring6restmvc.controllers;



import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;


@SpringBootTest
public class BeerControllerTest {
    @Autowired
    BeerController controller;

    @Test
    public void testGetBeerByUuid() {
        System.out.println(controller.getBeerByUuid(UUID.randomUUID()));
    }
}