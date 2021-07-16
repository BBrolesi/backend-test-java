package br.com.brunobrolesi.parking.service;

import br.com.brunobrolesi.parking.controller.dto.VehicleDto;
import br.com.brunobrolesi.parking.model.Parking;
import br.com.brunobrolesi.parking.model.Vehicle;
import br.com.brunobrolesi.parking.repositories.VehicleRepository;
import br.com.brunobrolesi.parking.util.ParkingCreator;
import br.com.brunobrolesi.parking.util.VehicleCreator;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @BeforeEach
    void setup() {
        List<Vehicle> vehicleList = new ArrayList<>(List.of(VehicleCreator.createValidVehicle()));
        Optional <Vehicle> optionalVehicle = Optional.of(VehicleCreator.createValidVehicle());
        Vehicle vehicle = VehicleCreator.createValidVehicle();

        BDDMockito.when(vehicleRepository.findAll()).thenReturn(vehicleList);
        BDDMockito.when(vehicleRepository.findById(ArgumentMatchers.any())).thenReturn(optionalVehicle);
        BDDMockito.when(vehicleRepository.save(ArgumentMatchers.any())).thenReturn(vehicle);
        BDDMockito.doNothing().when(vehicleRepository).deleteById(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("findAll returns a list of vehicles when successful")
    void findAll_ReturnsListOfVehicles_WhenSuccessful() {
        List<Vehicle> expected =List.of(VehicleCreator.createValidVehicle());
        List<Vehicle> returned = vehicleService.findAll();

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
    @DisplayName("findById returns the correspondent vehicle when successful")
    void findById_ReturnsTheCorrectVehicle_WhenSuccessful() {
        Vehicle expected = VehicleCreator.createValidVehicle();
        Vehicle returned = vehicleService.findById(1);

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
    @DisplayName("create returns the created parking when successful")
    void create_ReturnsCreatedParking_WhenSuccessful() {
        Vehicle expected = VehicleCreator.createValidVehicle();
        Vehicle returned = vehicleService.create(VehicleCreator.createVehicle());

        Assertions.assertThat(returned).isNotNull();
        Assertions.assertThat(returned.getId()).isEqualTo(expected.getId());
        Assertions.assertThat(returned.getManufacturer()).isEqualTo(expected.getManufacturer());
        Assertions.assertThat(returned.getModel()).isEqualTo(expected.getModel());
        Assertions.assertThat(returned.getYear()).isEqualTo(expected.getYear());
        Assertions.assertThat(returned.getColor()).isEqualTo(expected.getColor());
        Assertions.assertThat(returned.getLicensePlate()).isEqualTo(expected.getLicensePlate());
    }

    @Test
    @DisplayName("findAll throws an exception when list is empty")
    void findAll_ThrowsException_WhenListIsEmpty() {
        BDDMockito.when(vehicleRepository.findAll()).thenReturn(Collections.emptyList());

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> this.vehicleService.findAll());
    }

    @Test
    @DisplayName("findById throws an exception when id is invalid")
    void findById_ReturnsEmptyBody_WhenNotFound() {
        BDDMockito.when(vehicleRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> this.vehicleService.findById(1));
    }

    @Test
    @DisplayName("create throws a exception when license plate is already registered")
    void create_ThrowsException_WhenLicensePlateIsAlreadyRegistered() {
        BDDMockito.when(vehicleRepository.findByLicensePlate(ArgumentMatchers.any()))
                .thenReturn(Optional.of(VehicleCreator.createValidVehicle()));

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> this.vehicleService.create(VehicleCreator.createVehicle()));
    }



}