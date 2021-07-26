package br.com.brunobrolesi.parking.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

@Entity
public class Vehicle implements Serializable {
    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String manufacturer;
    private String model;
    private String year;
    private String color;
    @Column(unique = true)
    private String licensePlate;
    private Integer type;

    public Vehicle() {
    }

    public Vehicle(Integer id, String manufacturer, String model, String year, String color, String licensePlate, VehicleType type) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.color = color;
        this.licensePlate = licensePlate;
        this.type = type.getId();
    }

    public Integer getId() {
        return id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getType() {
        return VehicleType.toEnum(this.type);
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer.toLowerCase(Locale.ENGLISH);
    }

    public void setModel(String model) {
        this.model = model.toLowerCase(Locale.ENGLISH);
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setColor(String color) {
        this.color = color.toLowerCase(Locale.ENGLISH);
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate.toUpperCase(Locale.ENGLISH);
    }

    public void setType(VehicleType type) {
        this.type = type.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return id.equals(vehicle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
