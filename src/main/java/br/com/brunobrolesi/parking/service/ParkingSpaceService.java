package br.com.brunobrolesi.parking.service;

import br.com.brunobrolesi.parking.model.Parking;
import br.com.brunobrolesi.parking.model.ParkingSpace;
import br.com.brunobrolesi.parking.repositories.ParkingRepository;
import br.com.brunobrolesi.parking.repositories.ParkingSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpaceService {

    @Autowired
    ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    ParkingRepository parkingRepository;


    public ParkingSpace findByParkingIdAndParkingSpaceId(Integer parkingId, Integer parkingSpaceId) {
        Optional<ParkingSpace> parkingSpace = parkingSpaceRepository.findByParkingIdAndParkingSpaceId(parkingId, parkingSpaceId);
        if(parkingSpace.isEmpty()) throw new IllegalArgumentException("Não encontrado");
        return parkingSpace.get();
    }

    public ParkingSpace update(Integer parkingId, Integer parkingSpaceId, ParkingSpace parkingSpace) {
        Optional<ParkingSpace> entity = parkingSpaceRepository.findByParkingIdAndParkingSpaceId(parkingId, parkingSpaceId);

        if (entity.isEmpty()) throw new IllegalArgumentException("Vaga não encontrada");

        entity.get().setState(parkingSpace.getState());
        entity.get().setType(parkingSpace.getVehicleType());

        return parkingSpaceRepository.save(entity.get());
    }

    public void delete(Integer parkingId, Integer parkingSpaceId) {
        ParkingSpace parkingSpace = findByParkingIdAndParkingSpaceId(parkingId, parkingSpaceId);
        parkingSpaceRepository.deleteById(parkingSpace.getId());
        return;
    }

    public ParkingSpace insert(Integer parkingId, ParkingSpace parkingSpace) {
        Optional<Parking> parking = parkingRepository.findById(parkingId);

        if (parking.isEmpty()) throw new IllegalArgumentException("Estabelecimento inválido");

        parkingSpace.setParking(parking.get());
        
        return parkingSpaceRepository.save(parkingSpace);
    }

    public List<ParkingSpace> createMany(List<ParkingSpace> parkingSpaces) {
        return parkingSpaceRepository.saveAll(parkingSpaces);
    }
}
