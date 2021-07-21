package br.com.brunobrolesi.parking.service;

import br.com.brunobrolesi.parking.model.*;
import br.com.brunobrolesi.parking.repositories.ParkingRepository;
import br.com.brunobrolesi.parking.repositories.ParkingSpaceRepository;
import br.com.brunobrolesi.parking.repositories.TicketRepository;
import br.com.brunobrolesi.parking.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public Ticket entry(Integer parkingId, String vehiclePlate) {
        Optional<Parking> parking = parkingRepository.findById(parkingId);

        if(parking.isEmpty()) throw new IllegalArgumentException("O id: " + parkingId + " não é valido");

        Optional<Vehicle> vehicle = vehicleRepository.findByLicensePlate(vehiclePlate);

        if(vehicle.isEmpty()) throw new IllegalArgumentException("O veiculo com placa: " + vehiclePlate + " não é valido");

        List<ParkingSpace> parkingSpaces;
        parkingSpaces = parking.get().getParkingSpaces();
        Optional<ParkingSpace> parkingSpace = parkingSpaces
                .stream()
                .filter(element -> element.getVehicleType() == vehicle.get().getType() && element.getState() == ParkingSpaceState.FREE)
                .findFirst();

        if(parkingSpace.isEmpty()) throw new RuntimeException("Estacionamento Lotado");

        parkingSpace.get().setState(ParkingSpaceState.BUSY);
        Ticket ticket = new Ticket(null, vehicle.get(), parkingSpace.get());

        parkingSpaceRepository.save(parkingSpace.get());
        return ticketRepository.save(ticket);
    }

    public Ticket exit(Integer parkingId, Integer id) {
        Optional<Parking> parking = parkingRepository.findById(parkingId);

        if(parking.isEmpty()) throw new IllegalArgumentException("O id: " + parkingId + " não é valido");

        Optional<Ticket> ticket = ticketRepository.findById(id);

        if(ticket.isEmpty()) throw new IllegalArgumentException("O id: " + id + " não é valido");
        if(ticket.get().getExitTime() != null) throw new IllegalArgumentException("O ticket informado já foi utilizado");

        ticket.get().setExitTime(LocalDate.now());
        ticket.get().getParkingSpace().setState(ParkingSpaceState.FREE);

        return ticketRepository.save(ticket.get());
    }
}
