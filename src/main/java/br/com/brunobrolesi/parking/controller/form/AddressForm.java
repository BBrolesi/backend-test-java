package br.com.brunobrolesi.parking.controller.form;

import br.com.brunobrolesi.parking.model.Address;
import br.com.brunobrolesi.parking.model.City;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddressForm {

    @NotNull @NotEmpty
    private String street;
    @NotNull @NotEmpty
    private String number;
    private String address_2;
    @NotNull
    private Integer cityId;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Address converterAddress() {
        City city = new City(cityId, null, null );
        Address address = new Address(null, street, number, address_2, null, city);
        return address;
    }
}
