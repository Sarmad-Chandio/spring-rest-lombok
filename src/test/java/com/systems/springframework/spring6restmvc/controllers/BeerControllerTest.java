package com.systems.springframework.spring6restmvc.controllers;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BeerControllerTest extends TestCase {
    @Autowired
    BeerController controller;

    @Test
    public void testGetBeerByUuid() {
        System.out.println(controller.getBeerByUuid(UUID.randomUUID()));
    }
}