package br.com.brunobrolesi.parking.integration;

import br.com.brunobrolesi.parking.controller.dto.ParkingDto;
import br.com.brunobrolesi.parking.controller.dto.ParkingResumedDto;
import br.com.brunobrolesi.parking.controller.dto.VehicleDto;
import br.com.brunobrolesi.parking.model.*;
import br.com.brunobrolesi.parking.repositories.*;
import br.com.brunobrolesi.parking.service.AddressService;
import br.com.brunobrolesi.parking.service.ParkingSpaceService;
import br.com.brunobrolesi.parking.util.ParkingCreator;
import br.com.brunobrolesi.parking.util.ParkingFormCreator;
import br.com.brunobrolesi.parking.util.UpdateParkingFormCreator;
import br.com.brunobrolesi.parking.util.VehicleCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ParkingControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    private Parking createParking() {
        State state = new State(null, "São Paulo");
        City city = new City(null, "Santos", state);

        stateRepository.save(state);
        cityRepository.save(city);

        Parking parking = ParkingCreator.createParking();
        parking.getAddress().setCity(city);
        parking.getAddress().setParking(parking);
        addressRepository.save(parking.getAddress());
        Parking saved = parkingRepository.save(parking);
        parking.getParkingSpaces().stream().forEach(element -> element.setParking(parking));
        parkingSpaceRepository.saveAll(parking.getParkingSpaces());

        return saved;
    }

    @Test
    @DisplayName("ListParkings returns a list of parkings and 200 status code when successful")
    void listParkings_ReturnsListOfParkings_WhenSuccessful() {
        Parking saved = createParking();
        List<ParkingResumedDto> expected = ParkingResumedDto.converter(List.of(saved));
        ResponseEntity<List<ParkingResumedDto>> responseEntity = testRestTemplate.exchange("/estabelecimento", HttpMethod.GET, null, new ParameterizedTypeReference<List<ParkingResumedDto>>() {});

        HttpStatus statusCode = responseEntity.getStatusCode();
        List<ParkingResumedDto> returned = responseEntity.getBody();
        Assertions.assertThat(statusCode).isNotNull();
        Assertions.assertThat(statusCode.value()).isEqualTo(200);

        Assertions.assertThat(returned).isNotNull();
        Assertions.assertThat(returned).isNotEmpty().hasSize(1);
        Assertions.assertThat(returned.get(0).getId()).isEqualTo(expected.get(0).getId());
        Assertions.assertThat(returned.get(0).getCnpj()).isEqualTo(expected.get(0).getCnpj());
        Assertions.assertThat(returned.get(0).getName()).isEqualTo(expected.get(0).getName());
        Assertions.assertThat(returned.get(0).getPhones()).isEqualTo(expected.get(0).getPhones());
    }
}
