package br.com.brunobrolesi.parking.controller;

import br.com.brunobrolesi.parking.controller.form.*;
import br.com.brunobrolesi.parking.model.Parking;
import br.com.brunobrolesi.parking.controller.dto.ParkingDto;
import br.com.brunobrolesi.parking.controller.dto.ParkingResumedDto;
import br.com.brunobrolesi.parking.model.ParkingSpace;
import br.com.brunobrolesi.parking.model.Ticket;
import br.com.brunobrolesi.parking.service.ParkingService;
import br.com.brunobrolesi.parking.service.ParkingSpaceService;
import br.com.brunobrolesi.parking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/estabelecimento")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private ParkingSpaceService parkingSpaceService;

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<ParkingResumedDto>> listParkings() {
            List<Parking> parkings = parkingService.findAll();
            return ResponseEntity.ok().body(ParkingResumedDto.converter(parkings));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingDto> listParkingById(@PathVariable Integer id) {
            ParkingDto parking = new ParkingDto(parkingService.findById(id));
            return ResponseEntity.ok().body(parking);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteParking(@PathVariable Integer id) {
            parkingService.delete(id);
            return ResponseEntity.ok().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ParkingDto> createParking(@RequestBody @Valid ParkingForm form) {
            ParkingDto created = new ParkingDto(parkingService.create(form.converterParking()));
            return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ParkingDto> updateParking(@PathVariable Integer id, @RequestBody @Valid UpdateParkingForm form) {
            ParkingDto updated = new ParkingDto(parkingService.update(id, form.converterParking()));
            return ResponseEntity.ok().body(updated);
    }

    @GetMapping("/{parkingId}/vaga/{parkingSpaceId}")
    public ResponseEntity<ParkingSpace> findParkingSpaceById(@PathVariable Integer parkingId, @PathVariable Integer parkingSpaceId) {
            ParkingSpace parkingSpace = parkingSpaceService.findByParkingIdAndParkingSpaceId(parkingId, parkingSpaceId);
            return ResponseEntity.ok().body(parkingSpace);
    }

    @PutMapping("/{parkingId}/vaga/{parkingSpaceId}")
    @Transactional
    public ResponseEntity<ParkingSpace> updateParkingSpace(@PathVariable Integer parkingId, @PathVariable Integer parkingSpaceId, @RequestBody @Valid UpdateParkingSpaceForm form) {
            ParkingSpace parkingSpace = parkingSpaceService.update(parkingId, parkingSpaceId, form.converterParkingSpace());
            return ResponseEntity.ok().body(parkingSpace);
    }

    @DeleteMapping("/{parkingId}/vaga/{parkingSpaceId}")
    @Transactional
    public ResponseEntity<Void> deleteParkingSpace(@PathVariable Integer parkingId, @PathVariable Integer parkingSpaceId) {
            parkingSpaceService.delete(parkingId, parkingSpaceId);
            return ResponseEntity.ok().build();
    }

    @PostMapping("/{parkingId}/vaga")
    @Transactional
    public ResponseEntity<ParkingSpace> insertParkingSpace(@PathVariable Integer parkingId, @RequestBody @Valid ParkingSpaceForm form) {
            ParkingSpace parkingSpace = parkingSpaceService.insert(parkingId, form.converterParkingSpace());
            return ResponseEntity.ok().body(parkingSpace);
    }

    @PostMapping("/{parkingId}/entrar")
    @Transactional
    public ResponseEntity<Ticket> vehicleEntryRequest(@PathVariable Integer parkingId, @RequestBody EntryTicketForm form) {
            Ticket ticket = ticketService.entry(parkingId, form.getVehicleLicensePlate());
            return ResponseEntity.ok().body(ticket);
    }

    @PutMapping("/{parkingId}/sair/{ticketId}")
    @Transactional
    public ResponseEntity<Ticket> vehicleExitRequest(@PathVariable Integer parkingId, @PathVariable Integer ticketId) {
            Ticket ticket = ticketService.exit(parkingId, ticketId);
            return ResponseEntity.ok().body(ticket);
    }

}
