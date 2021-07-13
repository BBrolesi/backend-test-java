package br.com.brunobrolesi.parking.controller.dto;

import br.com.brunobrolesi.parking.model.Parking;
import br.com.brunobrolesi.parking.model.VehicleType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ParkingResumedDto {
    private Integer id;
    private String cnpj;
    private String name;
    private Set<String> phones = new HashSet<>();

    public ParkingResumedDto (Parking parking) {
        this.id = parking.getId();
        this.cnpj = parking.getCnpj();
        this.name = parking.getName();
        this.phones = parking.getPhones();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public static List<ParkingResumedDto> converter(List<Parking> parkings) {
        return parkings.stream().map(ParkingResumedDto::new).collect(Collectors.toList());
    }


}
