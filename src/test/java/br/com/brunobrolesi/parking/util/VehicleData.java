package br.com.brunobrolesi.parking.util;

import br.com.brunobrolesi.parking.model.VehicleType;

public class VehicleData {

    public static String getManufacturer() {
        return "ford";
    }
    public static String getModel() {
        return "fusion";
    }
    public static String getYear() {
        return "2018";
    }
    public static String getUpdatedYear() {
        return "2019";
    }
    public static String getColor() {
        return "preto";
    }
    public static String getLicensePlate() {
        return "alx1282";
    }
    public static VehicleType getType() {
        return VehicleType.CARRO;
    }
}
