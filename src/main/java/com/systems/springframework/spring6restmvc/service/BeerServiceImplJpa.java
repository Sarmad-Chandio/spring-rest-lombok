package com.systems.springframework.spring6restmvc.service;

import com.systems.springframework.spring6restmvc.mappers.BeerMapper;
import com.systems.springframework.spring6restmvc.model.BeerDTO;
import com.systems.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceImplJpa implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Override
    public List<BeerDTO> beerList() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BeerDTO> getBeerByUuid(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public BeerDTO saveBeerObject(BeerDTO beer) {
        return null;
    }

    @Override
    public void updateByUuid(UUID beerId, BeerDTO beer) {

    }

    @Override
    public void deleteByUuid(UUID beerId) {

    }

    @Override
    public void patchByUuid(UUID beerId, BeerDTO beer) {

    }
}
