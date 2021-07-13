package br.com.brunobrolesi.parking.controller.dto;

import br.com.brunobrolesi.parking.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ParkingDto {

    private Integer id;
    private String cnpj;
    private String name;
    private Address address;
    private Set<String> phones = new HashSet<>();
    private Integer carSpaces;
    private Integer motorcycleSpaces;
    private Integer freeCarSpaces;
    private Integer freeMotorcycleSpaces;
    private List<ParkingSpace> parkingSpaces = new ArrayList<>();

    public ParkingDto(Parking parking) {
        this.id = parking.getId();
        this.cnpj = parking.getCnpj();
        this.name = parking.getName();
        this.address = parking.getAddress();
        this.phones = parking.getPhones();
        this.parkingSpaces = parking.getParkingSpaces();

        this.carSpaces =  parking.getVehicleSpaceQuantity(VehicleType.CARRO);
        this.freeCarSpaces = parking.getFreeVehicleSpaceQuantity(VehicleType.CARRO);
        this.motorcycleSpaces = parking.getVehicleSpaceQuantity(VehicleType.MOTO);
        this.freeMotorcycleSpaces = parking.getFreeVehicleSpaceQuantity(VehicleType.MOTO);
    }

    public List<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(List<ParkingSpace> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public Integer getCarSpaces() {
        return carSpaces;
    }

    public void setCarSpaces(Integer carSpaces) {
        this.carSpaces = carSpaces;
    }

    public Integer getMotorcycleSpaces() {
        return motorcycleSpaces;
    }

    public void setMotorcycleSpaces(Integer motorcycleSpaces) {
        this.motorcycleSpaces = motorcycleSpaces;
    }

    public Integer getFreeCarSpaces() {
        return freeCarSpaces;
    }

    public void setFreeCarSpaces(Integer freeCarSpaces) {
        this.freeCarSpaces = freeCarSpaces;
    }

    public Integer getFreeMotorcycleSpaces() {
        return freeMotorcycleSpaces;
    }

    public void setFreeMotorcycleSpaces(Integer freeMotorcycleSpaces) {
        this.freeMotorcycleSpaces = freeMotorcycleSpaces;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public static List<ParkingDto> converter(List<Parking> parkings) {
        return parkings.stream().map(ParkingDto::new).collect(Collectors.toList());
    }
}
