package br.com.brunobrolesi.parking.controller;

import br.com.brunobrolesi.parking.controller.dto.ParkingDto;
import br.com.brunobrolesi.parking.controller.dto.ParkingResumedDto;
import br.com.brunobrolesi.parking.model.Parking;
import br.com.brunobrolesi.parking.service.ParkingService;
import br.com.brunobrolesi.parking.util.ParkingCreator;
import br.com.brunobrolesi.parking.util.ParkingFormCreator;
import br.com.brunobrolesi.parking.util.UpdateParkingFormCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
class ParkingControllerTest {

    @InjectMocks
    private ParkingController parkingController;

    @Mock
    private ParkingService parkingService;

    @BeforeEach
    void setup() {
        List<Parking> parkingList = new ArrayList<>(List.of(ParkingCreator.createValidParking()));
        Parking parking = ParkingCreator.createValidParking();

        BDDMockito.when(parkingService.findAll()).thenReturn(parkingList);
        BDDMockito.when(parkingService.findById(ArgumentMatchers.any())).thenReturn(parking);
        BDDMockito.doNothing().when(parkingService).delete(ArgumentMatchers.any());
        BDDMockito.when(parkingService.create(ParkingCreator.createParking())).thenReturn(ParkingCreator.createValidParking());
        BDDMockito.when(parkingService.update(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(ParkingCreator.createValidUpdatedParking());
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
    @DisplayName("updateParking returns the updated parking and 200 status code when successful")
    void updateParking_ReturnsTheUpdatedParking_WhenSuccessful(){
        ParkingDto expected = new ParkingDto(ParkingCreator.createValidUpdatedParking());
        ParkingDto returned = parkingController.updateParking(1, UpdateParkingFormCreator.createUpdateParkingForm()).getBody();
        HttpStatus statusCode = parkingController.updateParking(1, UpdateParkingFormCreator.createUpdateParkingForm()).getStatusCode();

        Assertions.assertThat(statusCode).isNotNull();
        Assertions.assertThat(statusCode.value()).isEqualTo(200);

        Assertions.assertThat(returned).isNotNull();
        Assertions.assertThat(returned.getId()).isEqualTo(expected.getId());
        Assertions.assertThat(returned.getName()).isEqualTo(expected.getName());
        Assertions.assertThat(returned.getAddress()).isEqualTo(expected.getAddress());
        Assertions.assertThat(returned.getPhones()).isEqualTo(expected.getPhones());
    }

    @Test
    @DisplayName("ListParkings must throw a exception when list is empty")
    void listParkings_ThrowsException_WhenNotFound() {
        BDDMockito.when(parkingService.findAll()).thenThrow(RuntimeException.class);

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> parkingController.listParkings());
    }

    @Test
    @DisplayName("listParkingById must throw a exception when didn't found the parking")
    void listParkingById_ThrowsException_WhenNotFound() {
        BDDMockito.when(parkingService.findById(ArgumentMatchers.any())).thenThrow(RuntimeException.class);

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> parkingController.listParkingById(1));
    }

    @Test
    @DisplayName("deleteParking must throw a exception when didn't found the parking")
    void deleteParking_ThrowsException_WhenNotFound() {
        BDDMockito.doThrow(RuntimeException.class).when(parkingService).delete(ArgumentMatchers.any());

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> parkingController.deleteParking(1));
    }

    @Test
    @DisplayName("createParking must throw a exception when fails")
    void createParking_ThrowsException_WhenFails(){
        BDDMockito.when(parkingService.create(ArgumentMatchers.any())).thenThrow(RuntimeException.class);

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> parkingController.createParking(ParkingFormCreator.createParkingForm()));
    }

    @Test
    @DisplayName("updateParking must throw a exception when fails")
    void updateParking_ThrowsException_WhenFails(){
        BDDMockito.when(parkingService.update(ArgumentMatchers.any(), ArgumentMatchers.any())).thenThrow(RuntimeException.class);

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> parkingController.updateParking(1, UpdateParkingFormCreator.createUpdateParkingForm()));
    }

}