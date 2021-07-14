package br.com.brunobrolesi.parking.controller;

import br.com.brunobrolesi.parking.controller.dto.ParkingDto;
import br.com.brunobrolesi.parking.controller.dto.ParkingResumedDto;
import br.com.brunobrolesi.parking.model.Parking;
import br.com.brunobrolesi.parking.service.ParkingService;
import br.com.brunobrolesi.parking.util.ParkingCreator;
import br.com.brunobrolesi.parking.util.ParkingFormCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ParkingControllerTest {

    @InjectMocks
    private ParkingController parkingController;

    @Mock
    private ParkingService parkingService;

    @BeforeEach
    void setUp() {
        List<Parking> parkingList = new ArrayList<>(List.of(ParkingCreator.createValidParking()));
        Parking parking = ParkingCreator.createValidParking();

        BDDMockito.when(parkingService.findAll()).thenReturn(parkingList);
        BDDMockito.when(parkingService.findById(ArgumentMatchers.any())).thenReturn(parking);
        BDDMockito.doNothing().when(parkingService).delete(ArgumentMatchers.any());
        BDDMockito.when(parkingService.create(ParkingCreator.createParking())).thenReturn(ParkingCreator.createValidParking());
    }

    @Test
    @DisplayName("ListParkings returns a list of parkings and 200 status code when successful")
    void listParkings_ReturnsListOfParkings_WhenSuccessful() {
        List<ParkingResumedDto> expected = ParkingResumedDto.converter(List.of(ParkingCreator.createValidParking()));
        List<ParkingResumedDto> returned = parkingController.listParkings().getBody();
        HttpStatus statusCode = parkingController.listParkings().getStatusCode();

        Assertions.assertThat(statusCode).isNotNull();
        Assertions.assertThat(statusCode.value()).isEqualTo(200);

        Assertions.assertThat(returned).isNotNull();
        Assertions.assertThat(returned).isNotEmpty().hasSize(1);
        Assertions.assertThat(returned.get(0).getId()).isEqualTo(expected.get(0).getId());
        Assertions.assertThat(returned.get(0).getCnpj()).isEqualTo(expected.get(0).getCnpj());
        Assertions.assertThat(returned.get(0).getName()).isEqualTo(expected.get(0).getName());
        Assertions.assertThat(returned.get(0).getPhones()).isEqualTo(expected.get(0).getPhones());
    }

    @Test
    @DisplayName("listParkingById returns the correspondent parking and 200 status code when successful")
    void listParkingById_ReturnsTheCorrectParking_WhenSuccessful() {
        ParkingDto expected = new ParkingDto(ParkingCreator.createValidParking());
        ParkingDto returned = parkingController.listParkingById(1).getBody();
        HttpStatus statusCode = parkingController.listParkingById(1).getStatusCode();

        Assertions.assertThat(statusCode).isNotNull();
        Assertions.assertThat(statusCode.value()).isEqualTo(200);

        Assertions.assertThat(returned).isNotNull();
        Assertions.assertThat(returned.getId()).isEqualTo(expected.getId());
        Assertions.assertThat(returned.getCnpj()).isEqualTo(expected.getCnpj());
        Assertions.assertThat(returned.getName()).isEqualTo(expected.getName());
        Assertions.assertThat(returned.getPhones()).isEqualTo(expected.getPhones());
    }

    @Test
    @DisplayName("createParking returns the created parking and 201 status code when successful")
    void createParking_ReturnsTheCreatedParking_WhenSuccessful(){
        ParkingDto expected = new ParkingDto(ParkingCreator.createValidParking());
        ParkingDto returned = parkingController.createParking(ParkingFormCreator.createParkingForm()).getBody();
        HttpStatus statusCode = parkingController.createParking(ParkingFormCreator.createParkingForm()).getStatusCode();

        Assertions.assertThat(statusCode).isNotNull();
        Assertions.assertThat(statusCode.value()).isEqualTo(201);

        Assertions.assertThat(returned).isNotNull();
        Assertions.assertThat(returned.getId()).isEqualTo(expected.getId());
        Assertions.assertThat(returned.getCnpj()).isEqualTo(expected.getCnpj());
        Assertions.assertThat(returned.getName()).isEqualTo(expected.getName());
        Assertions.assertThat(returned.getPhones()).isEqualTo(expected.getPhones());
    }

    @Test
    @DisplayName("deleteParking returns empty body and 200 status code when successful")
    void deleteParking_ReturnsEmptyBody_WhenSuccessful() {
        Void returned = parkingController.deleteParking(1).getBody();
        HttpStatus statusCode = parkingController.deleteParking(1).getStatusCode();

        Assertions.assertThat(returned).isNull();
        Assertions.assertThat(statusCode.value()).isEqualTo(200);
    }

    @Test
    @DisplayName("ListParkings returns empty body and 404 status code when list is empty")
    void listParkings_ReturnsEmptyBody_WhenNotFound() {
        BDDMockito.when(parkingService.findAll()).thenThrow(RuntimeException.class);

        List<ParkingResumedDto> returned = parkingController.listParkings().getBody();
        HttpStatus statusCode = parkingController.listParkings().getStatusCode();

        Assertions.assertThat(returned).isNull();
        Assertions.assertThat(statusCode.value()).isEqualTo(404);
    }

    @Test
    @DisplayName("listParkingById returns empty body and 404 status code when didn't found the parking")
    void listParkingById_ReturnsEmptyBody_WhenNotFound() {
        BDDMockito.when(parkingService.findById(ArgumentMatchers.any())).thenThrow(RuntimeException.class);

        ParkingDto returned = parkingController.listParkingById(1).getBody();
        HttpStatus statusCode = parkingController.listParkingById(1).getStatusCode();

        Assertions.assertThat(returned).isNull();
        Assertions.assertThat(statusCode.value()).isEqualTo(404);
    }

    @Test
    @DisplayName("deleteParking returns empty body and 404 status code when didn't found the parking")
    void deleteParking_ReturnsEmptyBody_WhenNotFound() {
        BDDMockito.doThrow(RuntimeException.class).when(parkingService).delete(ArgumentMatchers.any());

        Void returned = parkingController.deleteParking(1).getBody();
        HttpStatus statusCode = parkingController.deleteParking(1).getStatusCode();

        Assertions.assertThat(returned).isNull();
        Assertions.assertThat(statusCode.value()).isEqualTo(404);
    }

    @Test
    @DisplayName("createParking returns empty body and 422 status code when fails")
    void createParking_ReturnsEmptyBody_WhenFails(){
        BDDMockito.when(parkingService.create(ArgumentMatchers.any())).thenThrow(RuntimeException.class);

        ParkingDto returned = parkingController.createParking(ParkingFormCreator.createParkingForm()).getBody();
        HttpStatus statusCode = parkingController.createParking(ParkingFormCreator.createParkingForm()).getStatusCode();

        Assertions.assertThat(statusCode).isNotNull();
        Assertions.assertThat(statusCode.value()).isEqualTo(422);
        Assertions.assertThat(returned).isNull();
    }

}