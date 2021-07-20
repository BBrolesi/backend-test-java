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
        try {
            List<Parking> parkings = parkingService.findAll();
            return ResponseEntity.ok().body(ParkingResumedDto.converter(parkings));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingDto> listParkingById(@PathVariable Integer id) {
        try {
            ParkingDto parking = new ParkingDto(parkingService.findById(id));
            return ResponseEntity.ok().body(parking);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteParking(@PathVariable Integer id) {
        try {
            parkingService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ParkingDto> createParking(@RequestBody @Valid ParkingForm form) {
        try {
            ParkingDto created = new ParkingDto(parkingService.create(form.converterParking()));
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ParkingDto> updateParking(@PathVariable Integer id, @RequestBody @Valid UpdateParkingForm form) {
        try {
            ParkingDto updated = new ParkingDto(parkingService.update(id, form.converterParking()));
            return ResponseEntity.ok().body(updated);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping("/{parkingId}/vaga/{parkingSpaceId}")
    public ResponseEntity<ParkingSpace> findParkingSpaceById(@PathVariable Integer parkingId, @PathVariable Integer parkingSpaceId) {
        try {
            ParkingSpace parkingSpace = parkingSpaceService.findByParkingIdAndParkingSpaceId(parkingId, parkingSpaceId);
            return ResponseEntity.ok().body(parkingSpace);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{parkingId}/vaga/{parkingSpaceId}")
    @Transactional
    public ResponseEntity<ParkingSpace> updateParkingSpace(@PathVariable Integer parkingId, @PathVariable Integer parkingSpaceId, @RequestBody @Valid UpdateParkingSpaceForm form) {
        try {
            ParkingSpace parkingSpace = parkingSpaceService.update(parkingId, parkingSpaceId, form.converterParkingSpace());
            return ResponseEntity.ok().body(parkingSpace);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{parkingId}/vaga/{parkingSpaceId}")
    @Transactional
    public ResponseEntity<Void> deleteParkingSpace(@PathVariable Integer parkingId, @PathVariable Integer parkingSpaceId) {
        try {
            parkingSpaceService.delete(parkingId, parkingSpaceId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{parkingId}/vaga")
    @Transactional
    public ResponseEntity<ParkingSpace> insertParkingSpace(@PathVariable Integer parkingId, @RequestBody @Valid ParkingSpaceForm form) {
        try {
            ParkingSpace parkingSpace = parkingSpaceService.insert(parkingId, form.converterParkingSpace());
            return ResponseEntity.ok().body(parkingSpace);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{parkingId}/entrar")
    @Transactional
    public ResponseEntity<Ticket> vehicleEntryRequest(@PathVariable Integer parkingId, @RequestBody EntryTicketForm form) {
        try {
            Ticket ticket = ticketService.entry(parkingId, form.getVehicleLicensePlate());
            return ResponseEntity.ok().body(ticket);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PutMapping("/{parkingId}/sair")
    @Transactional
    public ResponseEntity<Ticket> vehicleExitRequest(@PathVariable Integer parkingId, @RequestBody ExitTicketForm form) {
        try {
            Ticket ticket = ticketService.exit(parkingId, form.getId());
            return ResponseEntity.ok().body(ticket);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
