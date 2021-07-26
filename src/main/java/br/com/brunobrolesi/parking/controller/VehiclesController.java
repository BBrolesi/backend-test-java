package br.com.brunobrolesi.parking.controller;

import br.com.brunobrolesi.parking.controller.dto.VehicleDto;
import br.com.brunobrolesi.parking.controller.form.UpdateVehicleForm;
import br.com.brunobrolesi.parking.controller.form.VehicleForm;
import br.com.brunobrolesi.parking.model.Vehicle;
import br.com.brunobrolesi.parking.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/veiculo")
public class VehiclesController {

    @Autowired
    private VehicleService service;

    @GetMapping
    public ResponseEntity<List<VehicleDto>> list() {
        List<Vehicle> vehicles = service.findAll();
        return ResponseEntity.ok().body(VehicleDto.converter(vehicles));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDto> listById(@PathVariable Integer id) {
        Vehicle vehicle = service.findById(id);
        return ResponseEntity.ok().body(new VehicleDto(vehicle));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<VehicleDto> create(@RequestBody @Valid VehicleForm form) {
        VehicleDto created = new VehicleDto(service.create(form.converter()));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<VehicleDto> update(@PathVariable Integer id, @RequestBody @Valid UpdateVehicleForm form) {
        VehicleDto vehicle = new VehicleDto(service.update(id, form.converterVehicle()));
        return ResponseEntity.ok().body(vehicle);
    }
}
