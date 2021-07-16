package br.com.brunobrolesi.parking.controller;

import br.com.brunobrolesi.parking.model.Vehicle;
import br.com.brunobrolesi.parking.repositories.VehicleRepository;
import br.com.brunobrolesi.parking.controller.dto.VehicleDto;
import br.com.brunobrolesi.parking.controller.form.UpdateVehicleForm;
import br.com.brunobrolesi.parking.controller.form.VehicleForm;
import br.com.brunobrolesi.parking.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/veiculos")
public class VehiclesController {

    @Autowired
    private VehicleService service;

    @GetMapping
    public ResponseEntity<List<VehicleDto>> list() {
        try {
            List<Vehicle> vehicles = service.findAll();
            return ResponseEntity.ok().body(VehicleDto.converter(vehicles));
        } catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDto> listById(@PathVariable Integer id) {
        try {
            Vehicle vehicle = service.findById(id);
            return ResponseEntity.ok().body(new VehicleDto(vehicle));
        } catch (Exception exception){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<VehicleDto> create(@RequestBody @Valid VehicleForm form) {
        try {
            VehicleDto created = new VehicleDto(service.create(form.converter()));
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception exception) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<VehicleDto> update(@PathVariable Integer id, @RequestBody @Valid UpdateVehicleForm form)
    {
            Optional<Vehicle> optional = Optional.ofNullable(service.update(id, form.converterVehicle()));

            if(optional.isPresent()) {
                return ResponseEntity.ok().body(new VehicleDto(optional.get()));
            }
        return ResponseEntity.notFound().build();
    }
}
