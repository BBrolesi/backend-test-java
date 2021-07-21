package br.com.brunobrolesi.parking.service;

import br.com.brunobrolesi.parking.model.Address;
import br.com.brunobrolesi.parking.model.City;
import br.com.brunobrolesi.parking.repositories.AddressRepository;
import br.com.brunobrolesi.parking.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CityRepository cityRepository;

    public Address create (Address address) {
        Integer cityId = address.getCity().getId();
        Optional<City> city =  cityRepository.findById(cityId);
        if(city.isEmpty()) throw new IllegalArgumentException("Cidade Inválida");
        address.setCity(city.get());
        return addressRepository.save(address);
    }
}
