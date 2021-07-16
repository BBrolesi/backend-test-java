package br.com.brunobrolesi.parking.util;

import br.com.brunobrolesi.parking.model.VehicleType;

public class VehicleData {

    public static String getManufacturer() {
        return "Ford";
    }
    public static String getModel() {
        return "Fusion";
    }
    public static String getYear() {
        return "2018";
    }
    public static String getColor() {
        return "Preto";
    }
    public static String getLicensePlate() {
        return "ALX1282";
    }
    public static VehicleType getType() {
        return VehicleType.CARRO;
    }

    public static String getUpdatedLicensePlate() {
        return "ALX1283";
    }
}
