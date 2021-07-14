package br.com.brunobrolesi.parking.service;

import br.com.brunobrolesi.parking.controller.dto.ParkingResumedDto;
import br.com.brunobrolesi.parking.model.Parking;
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
    void listParkings_ReturnsListOfParkings_WhenSuccessful() {
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
    @DisplayName("findById return parking when successful")
    void findById_ReturnParking_WhenSuccessful() {
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
}