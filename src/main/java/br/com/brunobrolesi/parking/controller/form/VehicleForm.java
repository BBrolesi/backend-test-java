package br.com.brunobrolesi.parking.controller.form;

import br.com.brunobrolesi.parking.model.Vehicle;
import br.com.brunobrolesi.parking.model.VehicleType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class VehicleForm {

    @NotNull @NotEmpty
    private String manufacturer;
    @NotNull @NotEmpty
    private String model;
    @NotNull @NotEmpty
    private String year;
    @NotNull @NotEmpty
    private String color;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "[A-Z]{2,3}[0-9]{4}|[A-Z]{3,4}[0-9]{3}|[A-Z0-9]{7}")
    private String licensePlate;
    @NotNull
    private Integer type;

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Vehicle converter() {
        return new Vehicle(null, manufacturer, model, year, color, licensePlate, VehicleType.toEnum(type));
    }
}
