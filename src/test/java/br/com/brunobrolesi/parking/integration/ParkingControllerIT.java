package br.com.brunobrolesi.parking.integration;

import br.com.brunobrolesi.parking.controller.dto.ParkingDto;
import br.com.brunobrolesi.parking.controller.dto.ParkingResumedDto;
import br.com.brunobrolesi.parking.controller.dto.VehicleDto;
import br.com.brunobrolesi.parking.model.Address;
import br.com.brunobrolesi.parking.model.City;
import br.com.brunobrolesi.parking.model.Parking;
import br.com.brunobrolesi.parking.model.State;
import br.com.brunobrolesi.parking.repositories.*;
import br.com.brunobrolesi.parking.service.AddressService;
import br.com.brunobrolesi.parking.service.ParkingSpaceService;
import br.com.brunobrolesi.parking.util.ParkingCreator;
import br.com.brunobrolesi.parking.util.ParkingFormCreator;
import br.com.brunobrolesi.parking.util.UpdateParkingFormCreator;
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

//    @Test
//    @DisplayName("listParkingById returns the correspondent parking and 200 status code when successful")
//    void listParkingById_ReturnsTheCorrectParking_WhenSuccessful() {
//        ParkingDto expected = new ParkingDto(ParkingCreator.createValidParking());
//        ParkingDto returned = parkingController.listParkingById(1).getBody();
//        HttpStatus statusCode = parkingController.listParkingById(1).getStatusCode();
//
//        Assertions.assertThat(statusCode).isNotNull();
//        Assertions.assertThat(statusCode.value()).isEqualTo(200);
//
//        Assertions.assertThat(returned).isNotNull();
//        Assertions.assertThat(returned.getId()).isEqualTo(expected.getId());
//        Assertions.assertThat(returned.getCnpj()).isEqualTo(expected.getCnpj());
//        Assertions.assertThat(returned.getName()).isEqualTo(expected.getName());
//        Assertions.assertThat(returned.getPhones()).isEqualTo(expected.getPhones());
//    }
//
//    @Test
//    @DisplayName("createParking returns the created parking and 201 status code when successful")
//    void createParking_ReturnsTheCreatedParking_WhenSuccessful(){
//        ParkingDto expected = new ParkingDto(ParkingCreator.createValidParking());
//        ParkingDto returned = parkingController.createParking(ParkingFormCreator.createParkingForm()).getBody();
//        HttpStatus statusCode = parkingController.createParking(ParkingFormCreator.createParkingForm()).getStatusCode();
//
//        Assertions.assertThat(statusCode).isNotNull();
//        Assertions.assertThat(statusCode.value()).isEqualTo(201);
//
//        Assertions.assertThat(returned).isNotNull();
//        Assertions.assertThat(returned.getId()).isEqualTo(expected.getId());
//        Assertions.assertThat(returned.getCnpj()).isEqualTo(expected.getCnpj());
//        Assertions.assertThat(returned.getName()).isEqualTo(expected.getName());
//        Assertions.assertThat(returned.getPhones()).isEqualTo(expected.getPhones());
//    }
//
//    @Test
//    @DisplayName("deleteParking returns empty body and 200 status code when successful")
//    void deleteParking_ReturnsEmptyBody_WhenSuccessful() {
//        Void returned = parkingController.deleteParking(1).getBody();
//        HttpStatus statusCode = parkingController.deleteParking(1).getStatusCode();
//
//        Assertions.assertThat(returned).isNull();
//        Assertions.assertThat(statusCode.value()).isEqualTo(200);
//    }
//
//    @Test
//    @DisplayName("updateParking returns the updated parking and 200 status code when successful")
//    void updateParking_ReturnsTheUpdatedParking_WhenSuccessful(){
//        ParkingDto expected = new ParkingDto(ParkingCreator.createValidUpdatedParking());
//        ParkingDto returned = parkingController.updateParking(1, UpdateParkingFormCreator.createUpdateParkingForm()).getBody();
//        HttpStatus statusCode = parkingController.updateParking(1, UpdateParkingFormCreator.createUpdateParkingForm()).getStatusCode();
//
//        Assertions.assertThat(statusCode).isNotNull();
//        Assertions.assertThat(statusCode.value()).isEqualTo(200);
//
//        Assertions.assertThat(returned).isNotNull();
//        Assertions.assertThat(returned.getId()).isEqualTo(expected.getId());
//        Assertions.assertThat(returned.getName()).isEqualTo(expected.getName());
//        Assertions.assertThat(returned.getAddress()).isEqualTo(expected.getAddress());
//        Assertions.assertThat(returned.getPhones()).isEqualTo(expected.getPhones());
//    }
//
//    @Test
//    @DisplayName("ListParkings returns empty body and 404 status code when list is empty")
//    void listParkings_ReturnsEmptyBody_WhenNotFound() {
//        BDDMockito.when(parkingService.findAll()).thenThrow(RuntimeException.class);
//
//        List<ParkingResumedDto> returned = parkingController.listParkings().getBody();
//        HttpStatus statusCode = parkingController.listParkings().getStatusCode();
//
//        Assertions.assertThat(returned).isNull();
//        Assertions.assertThat(statusCode.value()).isEqualTo(404);
//    }
//
//    @Test
//    @DisplayName("listParkingById returns empty body and 404 status code when didn't found the parking")
//    void listParkingById_ReturnsEmptyBody_WhenNotFound() {
//        BDDMockito.when(parkingService.findById(ArgumentMatchers.any())).thenThrow(RuntimeException.class);
//
//        ParkingDto returned = parkingController.listParkingById(1).getBody();
//        HttpStatus statusCode = parkingController.listParkingById(1).getStatusCode();
//
//        Assertions.assertThat(returned).isNull();
//        Assertions.assertThat(statusCode.value()).isEqualTo(404);
//    }
//
//    @Test
//    @DisplayName("deleteParking returns empty body and 404 status code when didn't found the parking")
//    void deleteParking_ReturnsEmptyBody_WhenNotFound() {
//        BDDMockito.doThrow(RuntimeException.class).when(parkingService).delete(ArgumentMatchers.any());
//
//        Void returned = parkingController.deleteParking(1).getBody();
//        HttpStatus statusCode = parkingController.deleteParking(1).getStatusCode();
//
//        Assertions.assertThat(returned).isNull();
//        Assertions.assertThat(statusCode.value()).isEqualTo(404);
//    }
//
//    @Test
//    @DisplayName("createParking returns empty body and 422 status code when fails")
//    void createParking_ReturnsEmptyBody_WhenFails(){
//        BDDMockito.when(parkingService.create(ArgumentMatchers.any())).thenThrow(RuntimeException.class);
//
//        ParkingDto returned = parkingController.createParking(ParkingFormCreator.createParkingForm()).getBody();
//        HttpStatus statusCode = parkingController.createParking(ParkingFormCreator.createParkingForm()).getStatusCode();
//
//        Assertions.assertThat(statusCode).isNotNull();
//        Assertions.assertThat(statusCode.value()).isEqualTo(422);
//        Assertions.assertThat(returned).isNull();
//    }
//
//    @Test
//    @DisplayName("updateParking returns empty body and 422 status code when fails")
//    void updateParking_ReturnsEmptyBody_WhenFails(){
//        BDDMockito.when(parkingService.update(ArgumentMatchers.any(), ArgumentMatchers.any())).thenThrow(RuntimeException.class);
//
//        ParkingDto returned = parkingController.updateParking(1, UpdateParkingFormCreator.createUpdateParkingForm()).getBody();
//        HttpStatus statusCode = parkingController.updateParking(1, UpdateParkingFormCreator.createUpdateParkingForm()).getStatusCode();
//
//        Assertions.assertThat(statusCode).isNotNull();
//        Assertions.assertThat(statusCode.value()).isEqualTo(422);
//
//        Assertions.assertThat(returned).isNull();
//    }
}
