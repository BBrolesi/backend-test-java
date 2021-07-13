package br.com.brunobrolesi.parking.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Parking implements Serializable {
    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cnpj;
    private String name;

    @JsonManagedReference
    @OneToOne(mappedBy = "parking", orphanRemoval = true)
    private Address address;

    @ElementCollection
    @CollectionTable(name = "phone")
    private Set<String> phones = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingSpace> parkingSpaces = new ArrayList<>();

    public Parking() {
    }

    public Parking(Integer id, String cnpj, String name) {
        this.id = id;
        this.cnpj = cnpj;
        this.name = name;
    }

    public List<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(List<ParkingSpace> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
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

    public void addPhone(String number) {
        this.phones.add(number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parking parking = (Parking) o;
        return Objects.equals(id, parking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getVehicleSpaceQuantity(VehicleType type) {
        return Math.toIntExact(parkingSpaces.stream().filter(element -> element.getVehicleType() == type).count());
    }

    public Integer getFreeVehicleSpaceQuantity(VehicleType type) {
        return Math.toIntExact(parkingSpaces.stream()
                .filter(element -> (element.getState() == ParkingSpaceState.FREE && element.getVehicleType() == type)).count());
    }
}
