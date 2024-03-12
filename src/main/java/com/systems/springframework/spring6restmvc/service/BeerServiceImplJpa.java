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
import java.util.concurrent.atomic.AtomicReference;
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

        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateByUuid(UUID beerId, BeerDTO beer) {
        AtomicReference <Optional<BeerDTO>> optionalAtomicReference= new AtomicReference<>();

        beerRepository.findById(beerId)
                .ifPresentOrElse( foundBeer->{
                    foundBeer.setBeerName(beer.getBeerName());
                    foundBeer.setBeerStyle(beer.getBeerStyle());
                    foundBeer.setUpc(beer.getUpc());
                    foundBeer.setPrice(beer.getPrice());
                    optionalAtomicReference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(foundBeer))));
                },()->{
                    optionalAtomicReference.set(Optional.empty());
                });
        return optionalAtomicReference.get();
    }

    @Override
    public Boolean deleteByUuid(UUID beerId) {
        if (beerRepository.existsById(beerId)){
            beerRepository.deleteById(beerId);
            return true;
        }

        return false;
    }

    @Override
    public void patchByUuid(UUID beerId, BeerDTO beer) {

    }
}
