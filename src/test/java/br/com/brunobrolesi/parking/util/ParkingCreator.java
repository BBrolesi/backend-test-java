package br.com.brunobrolesi.parking.util;

import br.com.brunobrolesi.parking.model.Address;
import br.com.brunobrolesi.parking.model.City;
import br.com.brunobrolesi.parking.model.Parking;
import br.com.brunobrolesi.parking.model.State;

public class ParkingCreator {

    public static Parking createParking() {
        Parking parking = new Parking(null, ParkingData.getCnpj(), ParkingData.getName());
        State state = new State(null, "São Paulo");
        City city = new City(null, "Santos", state);
        Address address = new Address(null, ParkingData.getStreet(),
                ParkingData.getNumber(), ParkingData.getAddress_2(), parking, city);

        parking.addPhone(ParkingData.getPhone1());
        parking.addPhone(ParkingData.getPhone2());
        parking.setAddress(address);

        return parking;
    }

    public static Parking createValidParking() {
        Parking parking = new Parking(1, ParkingData.getCnpj(), ParkingData.getName());
        State state = new State(1, "São Paulo");
        City city = new City(1, "Santos", state);
        Address address = new Address(1, ParkingData.getStreet(), ParkingData.getNumber(),
                ParkingData.getAddress_2(), parking, city);

        parking.addPhone(ParkingData.getPhone1());
        parking.addPhone(ParkingData.getPhone2());
        parking.setAddress(address);

        return parking;
    }

    public static Parking createValidUpdatedParking() {
        Parking parking = new Parking(1, ParkingData.getCnpj(), ParkingData.getUpdatedName());
        State state = new State(1, "São Paulo");
        City city = new City(1, "Santos", state);
        Address address = new Address(1, ParkingData.getStreet(), ParkingData.getNumber(),
                ParkingData.getAddress_2(), parking, city);

        parking.addPhone(ParkingData.getUpdatedPhone1());
        parking.addPhone(ParkingData.getPhone2());

        parking.setAddress(address);

        return parking;

    }
}
