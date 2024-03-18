package com.systems.springframework.spring6restmvc.service;

import com.systems.springframework.spring6restmvc.model.BeerCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BeerCsvServiceImplTest {
    BeerCsvService beerCsvService= new BeerCsvServiceImpl();

    @Test
    void convertCSV() throws FileNotFoundException{
        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

        List<BeerCSVRecord> csvRecords= beerCsvService.consvertCsvFile(file);
        System.out.println(csvRecords.size());

        assertThat(csvRecords.size()).isGreaterThan(0);
    }

}