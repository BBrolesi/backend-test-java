package br.com.brunobrolesi.parking.service;

import br.com.brunobrolesi.parking.model.Address;
import br.com.brunobrolesi.parking.model.Parking;
import br.com.brunobrolesi.parking.model.ParkingSpace;
import br.com.brunobrolesi.parking.repositories.AddressRepository;
import br.com.brunobrolesi.parking.repositories.ParkingRepository;
import br.com.brunobrolesi.parking.repositories.ParkingSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingService {

    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;
    @Autowired
    private ParkingSpaceService parkingSpaceService;

    public List<Parking> findAll() {
        List<Parking> parkingList = parkingRepository.findAll();

        if (parkingList.isEmpty()) throw new EmptyResultDataAccessException("Nenhum resultado encontrado", 1);

        return parkingList;
    }

    public Parking findById(Integer id) {
        Optional<Parking> entity = parkingRepository.findById(id);

        if (entity.isEmpty()) throw new EmptyResultDataAccessException("Nenhum resultado encontrado", 1);

        return entity.get();
    }

    public Parking create(Parking parking) {

        addressService.create(parking.getAddress());
        Parking created = parkingRepository.save(parking);
        parkingSpaceService.createMany(parking.getParkingSpaces());
        return created;
    }

    public void delete(Integer id) {
        Optional<Parking> entity = parkingRepository.findById(id);

        if (entity.isEmpty()) throw new IllegalArgumentException("Este id não é valido");

        parkingRepository.deleteById(id);
    }

    public Parking update(Integer id, Parking obj) {
        Optional<Parking> entity = parkingRepository.findById(id);

        if (entity.isEmpty()) throw new IllegalArgumentException("Este id não é valido");

        updateData(entity.get(), obj);
        return parkingRepository.save(entity.get());
    }

    private void updateData(Parking entity, Parking obj) {
        entity.setName(obj.getName());
        entity.getAddress().setStreet(obj.getAddress().getStreet());
        entity.getAddress().setNumber(obj.getAddress().getNumber());
        entity.getAddress().setAddress_2(obj.getAddress().getAddress_2());
        entity.setPhones(obj.getPhones());
    }
}
