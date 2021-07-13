package br.com.brunobrolesi.parking.util;

import br.com.brunobrolesi.parking.model.Address;
import br.com.brunobrolesi.parking.model.City;
import br.com.brunobrolesi.parking.model.Parking;
import br.com.brunobrolesi.parking.model.State;

public class ParkingCreator {

    public static Parking createParking() {
        Parking parking = new Parking(null, "555.555.555", "Estacionamento Central");
        State state = new State(null, "São Paulo");
        City city = new City(null, "Santos", state);
        Address address = new Address(null,"Avenida Brasil","333",null, parking, city);

        parking.setAddress(address);

        return parking;
    }

    public static Parking createValidParking() {
        Parking parking = new Parking(1, "555.555.555", "Estacionamento Central");
        State state = new State(1, "São Paulo");
        City city = new City(1, "Santos", state);
        Address address = new Address(1,"Avenida Brasil","333",null, parking, city);

        parking.setAddress(address);
        parking.addPhone("99999-0000");
        return parking;
    }

    public static Parking createValidUpdatedParking() {
        Parking parking = new Parking(1, "555.555.555", "Estacionamento Central");
        State state = new State(1, "São Paulo");
        City city = new City(1, "Santos", state);
        Address address = new Address(1,"Avenida São Paulo","777",null, parking, city);

        parking.setAddress(address);

        return parking;

    }
}
