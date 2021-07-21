package br.com.brunobrolesi.parking.service;

import br.com.brunobrolesi.parking.model.Vehicle;
import br.com.brunobrolesi.parking.model.VehicleType;
import br.com.brunobrolesi.parking.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository repository;

    public List<Vehicle> findAll() {
        List <Vehicle> vehicleList = repository.findAll();

        if (vehicleList.isEmpty()) throw new EmptyResultDataAccessException("Nenhum resultado encontrado", 1);

        return vehicleList;
    }

    public Vehicle findById(Integer id) {
        Optional<Vehicle> optional = repository.findById(id);

        if(optional.isEmpty()) throw new EmptyResultDataAccessException("Nenhum resultado encontrado", 1);

        return optional.get();
    }

    public Vehicle create(Vehicle obj) {
        Optional<Vehicle> optional = repository.findByLicensePlate(obj.getLicensePlate());

        if(optional.isPresent()) throw new IllegalArgumentException("Placa já cadastrada");

        return repository.save(obj);
    }

    public void delete(Integer id) {
        Optional<Vehicle> optional = repository.findById(id);

        if (optional.isEmpty()) throw new IllegalArgumentException("Este id é invalido");

        repository.deleteById(id);
    }

    public Vehicle update(Integer id, Vehicle obj) {
        Optional<Vehicle> entity = repository.findById(id);

        if (entity.isEmpty()){
            throw new IllegalArgumentException("Este id é inválido");
        }

        updateData(entity.get(), obj);
        return repository.save(entity.get());
    }

    private void updateData(Vehicle entity, Vehicle obj) {

        entity.setManufacturer(obj.getManufacturer());
        entity.setModel(obj.getModel());
        entity.setYear(obj.getYear());
        entity.setColor(obj.getColor());
        entity.setType(obj.getType());
    }


}
