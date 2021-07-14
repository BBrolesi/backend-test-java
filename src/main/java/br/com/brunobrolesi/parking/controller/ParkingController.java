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
        ParkingSpace obj = parkingSpaceService.findByParkingIdAndParkingSpaceId(parkingId, parkingSpaceId);
        if (obj != null) return ResponseEntity.ok().body(obj);
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{parkingId}/vaga/{parkingSpaceId}")
    @Transactional
    public ResponseEntity<ParkingSpace> updateParkingSpace(@PathVariable Integer parkingId, @PathVariable Integer parkingSpaceId, @RequestBody @Valid UpdateParkingSpaceForm form) {
        ParkingSpace obj = parkingSpaceService.update(parkingId, parkingSpaceId, form.converterParkingSpace());
        if (obj != null) {
            return ResponseEntity.ok().body(obj);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{parkingId}/vaga/{parkingSpaceId}")
    @Transactional
    public ResponseEntity<Void> deleteParkingSpace(@PathVariable Integer parkingId, @PathVariable Integer parkingSpaceId) {
        boolean result = parkingSpaceService.delete(parkingId, parkingSpaceId);
        if (result) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{parkingId}/vaga")
    @Transactional
    public ResponseEntity<ParkingSpace> insertParkingSpace(@PathVariable Integer parkingId, @RequestBody @Valid ParkingSpaceForm form) {
        ParkingSpace obj = parkingSpaceService.insert(parkingId, form.converterParkingSpace());

        if (obj == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(obj);

    }

    @PostMapping("/{parkingId}/entrar")
    @Transactional
    public ResponseEntity<Ticket> vehicleEntryRequest(@PathVariable Integer parkingId, @RequestBody EntryTicketForm form) {
        Ticket obj = ticketService.entry(parkingId, form.getVehicleLicensePlate());

        if (obj == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(obj);

    }

    @PutMapping("/{parkingId}/sair")
    @Transactional
    public ResponseEntity<Ticket> vehicleExitRequest(@PathVariable Integer parkingId, @RequestBody ExitTicketForm form) {
        Ticket obj = ticketService.exit(parkingId, form.getId());

        if (obj == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(obj);

    }

}
