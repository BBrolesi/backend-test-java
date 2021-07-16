package br.com.brunobrolesi.parking.service;

import br.com.brunobrolesi.parking.controller.dto.ParkingResumedDto;
import br.com.brunobrolesi.parking.model.Parking;
import br.com.brunobrolesi.parking.repositories.AddressRepository;
import br.com.brunobrolesi.parking.repositories.ParkingRepository;
import br.com.brunobrolesi.parking.util.ParkingCreator;
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

@ExtendWith(SpringExtension.class)
class ParkingServiceTest {

    @InjectMocks
    private ParkingService parkingService;

    @Mock
    private ParkingRepository parkingRepository;
    @Mock
    private ParkingSpaceService parkingSpaceService;
    @Mock
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        ArrayList<Parking> parkingList = new ArrayList<>(List.of(ParkingCreator.createValidParking()));
        Optional<Parking> parking = Optional.of(ParkingCreator.createValidParking());

        BDDMockito.when(parkingRepository.findAll()).thenReturn(parkingList);
        BDDMockito.when(parkingRepository.findById(ArgumentMatchers.any())).thenReturn(parking);
        BDDMockito.doNothing().when(parkingRepository).delete(ArgumentMatchers.any());
        BDDMockito.when(parkingRepository.save(ParkingCreator.createParking())).thenReturn(ParkingCreator.createValidParking());
    }

    @Test
    @DisplayName("findAll returns a list of parkings when successful")
    void findAll_ReturnsListOfParkings_WhenSuccessful() {
        List<Parking> expected = List.of(ParkingCreator.createValidParking());
        List<Parking> returned = parkingService.findAll();

        Assertions.assertThat(returned).isNotNull();
        Assertions.assertThat(returned).isNotEmpty().hasSize(1);
        Assertions.assertThat(returned.get(0).getId()).isEqualTo(expected.get(0).getId());
        Assertions.assertThat(returned.get(0).getCnpj()).isEqualTo(expected.get(0).getCnpj());
        Assertions.assertThat(returned.get(0).getAddress()).isEqualTo(expected.get(0).getAddress());
        Assertions.assertThat(returned.get(0).getName()).isEqualTo(expected.get(0).getName());
        Assertions.assertThat(returned.get(0).getPhones()).isEqualTo(expected.get(0).getPhones());
    }

    @Test
    @DisplayName("findById returns the correct parking when successful")
    void findById_ReturnsParking_WhenSuccessful() {
        Parking expected = ParkingCreator.createValidParking();
        Parking returned = parkingService.findById(1);

        Assertions.assertThat(returned).isNotNull();
        Assertions.assertThat(returned.getId()).isEqualTo(expected.getId());
        Assertions.assertThat(returned.getCnpj()).isEqualTo(expected.getCnpj());
        Assertions.assertThat(returned.getAddress()).isEqualTo(expected.getAddress());
        Assertions.assertThat(returned.getName()).isEqualTo(expected.getName());
        Assertions.assertThat(returned.getPhones()).isEqualTo(expected.getPhones());
    }

    @Test
    @DisplayName("create returns the created parking when successful")
    void create_ReturnsCreatedParking_WhenSuccessful() {
        BDDMockito.when(parkingSpaceService.createMany(ArgumentMatchers.any())).thenReturn(ParkingCreator.createValidParking().getParkingSpaces());
        BDDMockito.when(addressService.create(ArgumentMatchers.any())).thenReturn(ParkingCreator.createValidParking().getAddress());

        Parking expected = ParkingCreator.createValidParking();
        Parking returned = parkingService.create(ParkingCreator.createParking());

        Assertions.assertThat(returned).isNotNull();
        Assertions.assertThat(returned.getId()).isEqualTo(expected.getId());
        Assertions.assertThat(returned.getCnpj()).isEqualTo(expected.getCnpj());
        Assertions.assertThat(returned.getAddress()).isEqualTo(expected.getAddress());
        Assertions.assertThat(returned.getName()).isEqualTo(expected.getName());
        Assertions.assertThat(returned.getPhones()).isEqualTo(expected.getPhones());
    }

    @Test
    @DisplayName("delete return void when successful")
    void delete_ReturnsVoid_WhenSuccessful() {
        Assertions.assertThatNoException()
                .isThrownBy(() -> this.parkingService.delete(1));
    }

    @Test
    @DisplayName("update return updated parking when successful")
    void update_ReturnsUpdatedParking_WhenSuccessful() {
        BDDMockito.when(parkingRepository.save(ArgumentMatchers.any())).thenReturn(ParkingCreator.createValidUpdatedParking());

        Parking expected = ParkingCreator.createValidUpdatedParking();
        Parking returned = parkingService.update(1, ParkingCreator.createParking());

        Assertions.assertThat(returned).isNotNull();
        Assertions.assertThat(returned.getId()).isEqualTo(expected.getId());
        Assertions.assertThat(returned.getCnpj()).isEqualTo(expected.getCnpj());
        Assertions.assertThat(returned.getAddress()).isEqualTo(expected.getAddress());
        Assertions.assertThat(returned.getName()).isEqualTo(expected.getName());
        Assertions.assertThat(returned.getPhones()).isEqualTo(expected.getPhones());
    }

    @Test
    @DisplayName("findAll throws a exception when list is empty")
    void findAll_ThrowsException_WhenListIsEmpty() {
        BDDMockito.when(parkingRepository.findAll()).thenReturn(Collections.emptyList());

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> this.parkingService.findAll());
    }

    @Test
    @DisplayName("findById throws a exception when parking not found")
    void findById_ThrowsException_WhenParkingIsNotFound() {
        BDDMockito.when(parkingRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> this.parkingService.findById(1));
    }

    @Test
    @DisplayName("create throws a exception when cityId is invalid")
    void create_ThrowsException_WhenCityIdNotValid() {
        BDDMockito.when(parkingSpaceService.createMany(ArgumentMatchers.any())).thenReturn(ParkingCreator.createValidParking().getParkingSpaces());
        BDDMockito.when(addressService.create(ArgumentMatchers.any())).thenThrow(RuntimeException.class);

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> this.parkingService.create(ParkingCreator.createParking()));
    }

    @Test
    @DisplayName("delete throws a exception when id is invalid")
    void delete_ThrowsException_WhenIdNotValid() {
        BDDMockito.when(parkingRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> this.parkingService.delete(1232));
    }

    @Test
    @DisplayName("update throws a exception when id is invalid")
    void update_ThrowsException_WhenIdNotValid() {
        BDDMockito.when(parkingRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> this.parkingService.update(22, ParkingCreator.createParking()));
    }


}