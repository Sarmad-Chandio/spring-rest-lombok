package com.systems.springframework.spring6restmvc.service;

import com.systems.springframework.spring6restmvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
    List<BeerCSVRecord> consvertCsvFile(File file);
}
