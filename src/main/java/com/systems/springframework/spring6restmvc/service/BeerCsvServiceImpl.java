package com.systems.springframework.spring6restmvc.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.systems.springframework.spring6restmvc.model.BeerCSVRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.util.List;
@Service
public class BeerCsvServiceImpl implements BeerCsvService {
    @Override
    public List<BeerCSVRecord> consvertCsvFile(File file) {
        try {
            List<BeerCSVRecord> csvRecords= new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(file))
                    .withType(BeerCSVRecord.class)
                    .build()
                    .parse();
            return csvRecords;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
