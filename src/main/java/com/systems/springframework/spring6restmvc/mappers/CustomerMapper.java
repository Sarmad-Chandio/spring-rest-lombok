package com.systems.springframework.spring6restmvc.mappers;

import com.systems.springframework.spring6restmvc.entities.Customer;
import com.systems.springframework.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer CustomerDtoToCustomer(CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDto(Customer customerDTO);
}
