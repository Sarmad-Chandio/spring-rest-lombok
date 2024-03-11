package com.systems.springframework.spring6restmvc.mappers;

import com.systems.springframework.spring6restmvc.entities.Beer;
import com.systems.springframework.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO beerDTO);

    BeerDTO beerToBeerDto(Beer beer);

}
