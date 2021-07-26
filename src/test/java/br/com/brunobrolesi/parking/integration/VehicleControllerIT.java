package br.com.brunobrolesi.parking.integration;

import br.com.brunobrolesi.parking.controller.dto.VehicleDto;
import br.com.brunobrolesi.parking.controller.form.UpdateVehicleForm;
import br.com.brunobrolesi.parking.model.Vehicle;
import br.com.brunobrolesi.parking.repositories.VehicleRepository;
import br.com.brunobrolesi.parking.util.UpdateVehicleFormCreator;
import br.com.brunobrolesi.parking.util.VehicleCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class VehicleControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private VehicleRepository repository;

    @Test
    @DisplayName("list returns a list of vehicles when successful")
    void list_ReturnsListOfVehicles_WhenSuccessful() {
        Vehicle saved = repository.save(VehicleCreator.createVehicle());

        List<VehicleDto> expected = VehicleDto.converter(List.of(saved));
        List<VehicleDto> returned = testRestTemplate.exchange("/veiculos", HttpMethod.GET, null, new ParameterizedTypeReference<List<VehicleDto>>() {}).getBody();

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
    @DisplayName("listById returns the correspondent vehicle when successful")
    void listById_ReturnsTheCorrectVehicle_WhenSuccessful() {
        Vehicle saved = repository.save(VehicleCreator.createVehicle());

        VehicleDto expected = new VehicleDto(saved);
        Integer expectedId = expected.getId();
        VehicleDto returned = testRestTemplate.getForObject("/veiculos/{id}", VehicleDto.class, expectedId);

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
    @DisplayName("delete returns empty body when successful")
    void delete_ReturnsEmptyBody_WhenSuccessful() {
        Vehicle saved = repository.save(VehicleCreator.createVehicle());

        ResponseEntity <Void> returned = testRestTemplate.exchange("/veiculos/{id}", HttpMethod.DELETE, null, Void.class, saved.getId());

        Assertions.assertThat(returned).isNotNull();
        Assertions.assertThat(returned.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("update returns the updated vehicle and 200 status code when successful")
    void update_ReturnsTheUpdatedVehicle_WhenSuccessful() {
        Vehicle saved = repository.save(VehicleCreator.createVehicle());

        UpdateVehicleForm form = UpdateVehicleFormCreator.createVehicleForm();
        VehicleDto expected = new VehicleDto(VehicleCreator.createValidUpdatedVehicle());
        VehicleDto returned = testRestTemplate.exchange("/veiculos/{id}", HttpMethod.PUT, new HttpEntity<>(form), VehicleDto.class, saved.getId()).getBody();

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
    @DisplayName("create returns 400 status code when fails")
    void create_ReturnsEmptyBody_WhenFails() {
        ResponseEntity <VehicleDto> returned = testRestTemplate.postForEntity("/veiculos", Void.class, VehicleDto.class);

        Assertions.assertThat(returned.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("update returns 400 status code when fails")
    void update_ReturnsEmptyBody_WhenFails() {
        ResponseEntity <VehicleDto> returned = testRestTemplate.exchange("/veiculos/{id}", HttpMethod.PUT, new HttpEntity<>(Void.class), VehicleDto.class, 1);

        Assertions.assertThat(returned.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
