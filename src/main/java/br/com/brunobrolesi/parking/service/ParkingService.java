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
    private AddressRepository addressRepository;
    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    public List<Parking> findAll() {
        List<Parking> parkingList = parkingRepository.findAll();

        if(parkingList.isEmpty()) throw new EmptyResultDataAccessException("Nenhum resultado encontrado", 1);

        return parkingList;
    }

    public Parking findById(Integer id) {
        Optional<Parking> obj = parkingRepository.findById(id);
        if(obj.isPresent()) return obj.get();
        return null;
    }

    public Parking create(Parking parking) {
        Address address = parking.getAddress();
        List<ParkingSpace> parkingSpaces = parking.getParkingSpaces();

        Parking inserted = parkingRepository.save(parking);
        addressRepository.save(address);
        parkingSpaceRepository.saveAll(parkingSpaces);

        return  inserted;
    }

    public void delete(Integer id) { parkingRepository.deleteById(id);}

    public Parking update(Integer id, Parking obj) {
        Optional<Parking> entity = parkingRepository.findById(id);
        if(entity.isPresent()) {
            updateData(entity.get(), obj);
            return parkingRepository.save(entity.get());
        }
        return null;
    }

    private void updateData(Parking entity, Parking obj) {
        entity.setName(obj.getName());
        entity.setPhones(obj.getPhones());
    }
}
