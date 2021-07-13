package br.com.brunobrolesi.parking.controller;

import br.com.brunobrolesi.parking.controller.dto.ParkingResumedDto;
import br.com.brunobrolesi.parking.model.Parking;
import br.com.brunobrolesi.parking.service.ParkingService;
import br.com.brunobrolesi.parking.util.ParkingCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ParkingControllerTest {

    @InjectMocks
    private ParkingController parkingController;

    @Mock
    private ParkingService parkingService;

    @BeforeEach
    void setUp(){
        List<Parking> parkingList = new ArrayList<>(List.of(ParkingCreator.createValidParking()));
        BDDMockito.when(parkingService.findAll()).thenReturn(parkingList);
    }

    @Test
    @DisplayName("ListParkings returns a list of parkings when successful")
    void listParkings_ReturnsListOfParkings_WhenSuccessful(){
        List<ParkingResumedDto> expected = ParkingResumedDto.converter(List.of(ParkingCreator.createValidParking()));
        List<ParkingResumedDto> returned = parkingController.listParkings();

        Assertions.assertThat(returned).isNotNull();
        Assertions.assertThat(returned).isNotEmpty().hasSize(1);
        Assertions.assertThat(returned.get(0).getId()).isEqualTo(expected.get(0).getId());
        Assertions.assertThat(returned.get(0).getCnpj()).isEqualTo(expected.get(0).getCnpj());
        Assertions.assertThat(returned.get(0).getName()).isEqualTo(expected.get(0).getName());
        Assertions.assertThat(returned.get(0).getPhones()).isEqualTo(expected.get(0).getPhones());
    }

}