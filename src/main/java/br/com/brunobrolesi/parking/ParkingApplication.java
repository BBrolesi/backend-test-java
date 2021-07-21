package br.com.brunobrolesi.parking;

import br.com.brunobrolesi.parking.model.*;
import br.com.brunobrolesi.parking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class ParkingApplication implements CommandLineRunner {

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ParkingRepository parkingRepository;

	@Autowired
	private ParkingSpaceRepository parkingSpaceRepository;

	public static void main(String[] args) {
		SpringApplication.run(ParkingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		State s1 = new State(null, "São Paulo");
		State s2 = new State(null, "Paraná");

		City c1 = new City(null, "Santos", s1);
		City c2 = new City(null, "Campo Mourão", s2);
		City c3 = new City(null, "São Paulo", s1);

		s1.getCities().addAll(Arrays.asList(c1, c3));
		s2.getCities().addAll(Arrays.asList(c2));

		stateRepository.saveAll(Arrays.asList(s1, s2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));
	}
}
