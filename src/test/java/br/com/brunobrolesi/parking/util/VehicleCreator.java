package br.com.brunobrolesi.parking.util;

import br.com.brunobrolesi.parking.model.Vehicle;

public class VehicleCreator {

    public static Vehicle createVehicle() {
        return new Vehicle(null,
                VehicleData.getManufacturer(),
                VehicleData.getModel(),
                VehicleData.getYear(),
                VehicleData.getColor(),
                VehicleData.getLicensePlate(),
                VehicleData.getType());
    }

    public static Vehicle createValidVehicle() {
        return new Vehicle(1,
                VehicleData.getManufacturer(),
                VehicleData.getModel(),
                VehicleData.getYear(),
                VehicleData.getColor(),
                VehicleData.getLicensePlate(),
                VehicleData.getType());
    }

    public static Vehicle createValidUpdatedVehicle() {
        return new Vehicle(1,
                VehicleData.getManufacturer(),
                VehicleData.getModel(),
                VehicleData.getYear(),
                VehicleData.getColor(),
                VehicleData.getUpdatedLicensePlate(),
                VehicleData.getType());
    }
}
