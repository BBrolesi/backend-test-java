package br.com.brunobrolesi.parking.controller;

import br.com.brunobrolesi.parking.controller.dto.ParkingDto;
import br.com.brunobrolesi.parking.controller.dto.ParkingResumedDto;
import br.com.brunobrolesi.parking.controller.dto.VehicleDto;
import br.com.brunobrolesi.parking.controller.form.VehicleForm;
import br.com.brunobrolesi.parking.model.Vehicle;
import br.com.brunobrolesi.parking.service.VehicleService;
import br.com.brunobrolesi.parking.util.ParkingCreator;
import br.com.brunobrolesi.parking.util.UpdateVehicleFormCreator;
import br.com.brunobrolesi.parking.util.VehicleCreator;
import br.com.brunobrolesi.parking.util.VehicleFormCreator;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class VehiclesControllerTest {

    @InjectMocks
    private VehiclesController vehiclesController;

    @Mock
    private VehicleService vehicleService;

    @BeforeEach
    void setup() {
        List<Vehicle> vehicleList = new ArrayList<>(List.of(VehicleCreator.createValidVehicle()));
        Vehicle vehicle = VehicleCreator.createValidVehicle();

        BDDMockito.when(vehicleService.findAll()).thenReturn(vehicleList);
        BDDMockito.when(vehicleService.findById(ArgumentMatchers.any())).thenReturn(vehicle);
        BDDMockito.when(vehicleService.create(ArgumentMatchers.any())).thenReturn(vehicle);
        BDDMockito.doNothing().when(vehicleService).delete(ArgumentMatchers.any());
        BDDMockito.when(vehicleService.update(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(VehicleCreator.createValidUpdatedVehicle());
    }


    @Test
    @DisplayName("list returns a list of vehicles and 200 status code when successful")
    void list_ReturnsListOfVehicles_WhenSuccessful() {
        List<VehicleDto> expected = VehicleDto.converter(List.of(VehicleCreator.createValidVehicle()));
        List<VehicleDto> returned = vehiclesController.list().getBody();
        HttpStatus statusCode = vehiclesController.list().getStatusCode();

        Assertions.assertThat(statusCode).isNotNull();
        Assertions.assertThat(statusCode.value()).isEqualTo(200);

        Assertions.assertThat(returned).isNotNull();
        Assertions.assertThat(returned).isNotEmpty().hasSize(1);
        Assertions.assertThat(returned.get(0).getId()).isEqualTo(expected.get(0).getId());
        Assertions.assertThat(returned.get(0).getManufacturer()).isEqualTo(expected.get(0).getManufacturer());
        Assertions.assertThat(returned.get(0).getModel()).isEqualTo(expected.get(0).getModel());
        Assertions.assertThat(returned.get(0).getYear()).isEqualTo(expected.get(0).getYear());
        Assertions.assertThat(returned.get(0).getColor()).isEqualTo(expected.get(0).getColor());
        Assertions.assertThat(returned.get(0).getLicensePlate()).isEqualTo(expected.get(0).getLicensePlate());
        Assertions.assertThat(returned.get(0).getType()).isEqualTo(expected.get(0).getType());
    }

    @Test
    @DisplayName("listById returns the correspondent vehicle and 200 status code when successful")
    void listById_ReturnsTheCorrectVehicle_WhenSuccessful() {
        VehicleDto expected = new VehicleDto(VehicleCreator.createValidVehicle());
        VehicleDto returned = vehiclesController.listById(1).getBody();
        HttpStatus statusCode = vehiclesController.listById(1).getStatusCode();

        Assertions.assertThat(statusCode).isNotNull();
        Assertions.assertThat(statusCode.value()).isEqualTo(200);

        Assertions.assertThat(returned).isNotNull();
        Assertions.assertThat(returned.getId()).isEqualTo(expected.getId());
        Assertions.assertThat(returned.getManufacturer()).isEqualTo(expected.getManufacturer());
        Assertions.assertThat(returned.getModel()).isEqualTo(expected.getModel());
        Assertions.assertThat(returned.getYear()).isEqualTo(expected.getYear());
        Assertions.assertThat(returned.getColor()).isEqualTo(expected.getColor());
        Assertions.assertThat(returned.getLicensePlate()).isEqualTo(expected.getLicensePlate());
        Assertions.assertThat(returned.getType()).isEqualTo(expected.getType());
    }

    @Test
    @DisplayName("create returns the created vehicle and 201 status code when successful")
    void create_ReturnsTheCreatedVehicle_WhenSuccessful() {
        VehicleDto expected = new VehicleDto(VehicleCreator.createValidVehicle());
        VehicleDto returned = vehiclesController.create(VehicleFormCreator.createVehicleForm()).getBody();
        HttpStatus statusCode = vehiclesController.create(VehicleFormCreator.createVehicleForm()).getStatusCode();

        Assertions.assertThat(statusCode).isNotNull();
        Assertions.assertThat(statusCode.value()).isEqualTo(201);

        Assertions.assertThat(returned).isNotNull();
        Assertions.assertThat(returned.getId()).isEqualTo(expected.getId());
        Assertions.assertThat(returned.getManufacturer()).isEqualTo(expected.getManufacturer());
        Assertions.assertThat(returned.getModel()).isEqualTo(expected.getModel());
        Assertions.assertThat(returned.getYear()).isEqualTo(expected.getYear());
        Assertions.assertThat(returned.getColor()).isEqualTo(expected.getColor());
        Assertions.assertThat(returned.getLicensePlate()).isEqualTo(expected.getLicensePlate());
        Assertions.assertThat(returned.getType()).isEqualTo(expected.getType());
    }

    @Test
    @DisplayName("delete returns empty body and 200 status code when successful")
    void delete_ReturnsEmptyBody_WhenSuccessful() {
        Void returned = vehiclesController.delete(1).getBody();
        HttpStatus statusCode = vehiclesController.delete(1).getStatusCode();

        Assertions.assertThat(returned).isNull();
        Assertions.assertThat(statusCode.value()).isEqualTo(200);
    }

    @Test
    @DisplayName("update returns the updated vehicle and 200 status code when successful")
    void update_ReturnsTheUpdatedVehicle_WhenSuccessful() {
        VehicleDto expected = new VehicleDto(VehicleCreator.createValidUpdatedVehicle());
        VehicleDto returned = vehiclesController.update(1, UpdateVehicleFormCreator.createVehicleForm()).getBody();
        HttpStatus statusCode = vehiclesController.update(1, UpdateVehicleFormCreator.createVehicleForm()).getStatusCode();

        Assertions.assertThat(statusCode).isNotNull();
        Assertions.assertThat(statusCode.value()).isEqualTo(200);

        Assertions.assertThat(returned).isNotNull();
        Assertions.assertThat(returned.getId()).isEqualTo(expected.getId());
        Assertions.assertThat(returned.getManufacturer()).isEqualTo(expected.getManufacturer());
        Assertions.assertThat(returned.getModel()).isEqualTo(expected.getModel());
        Assertions.assertThat(returned.getYear()).isEqualTo(expected.getYear());
        Assertions.assertThat(returned.getColor()).isEqualTo(expected.getColor());
        Assertions.assertThat(returned.getLicensePlate()).isEqualTo(expected.getLicensePlate());
        Assertions.assertThat(returned.getType()).isEqualTo(expected.getType());
    }

    @Test
    @DisplayName("list must throw a exception when list is empty")
    void list_ThrowsException_WhenNotFound() {
        BDDMockito.when(vehicleService.findAll()).thenThrow(RuntimeException.class);

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> vehiclesController.list());
    }

    @Test
    @DisplayName("listById must throw a exception when vehicle not found")
    void listById_ThrowsException_WhenNotFound() {
        BDDMockito.when(vehicleService.findById(ArgumentMatchers.any())).thenThrow(RuntimeException.class);

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> vehiclesController.listById(1));
    }

    @Test
    @DisplayName("create must throw a exception when fails")
    void create_ThrowsException_WhenFails() {
        BDDMockito.when(vehicleService.create(ArgumentMatchers.any())).thenThrow(RuntimeException.class);

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> vehiclesController.create(VehicleFormCreator.createVehicleForm()));
    }

    @Test
    @DisplayName("delete must throw a exception when didn't found the vehicle")
    void delete_ThrowsException_WhenNotFound() {
        BDDMockito.doThrow(RuntimeException.class).when(vehicleService).delete(ArgumentMatchers.any());

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> vehiclesController.delete(1));
    }

    @Test
    @DisplayName("update must throw a exception when fails")
    void update_ThrowsException_WhenFails() {
        BDDMockito.when(vehicleService.update(ArgumentMatchers.any(), ArgumentMatchers.any())).thenThrow(RuntimeException.class);

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> vehiclesController.update(1, UpdateVehicleFormCreator.createVehicleForm()));
    }


}