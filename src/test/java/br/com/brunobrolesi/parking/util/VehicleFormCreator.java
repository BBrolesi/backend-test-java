package br.com.brunobrolesi.parking.util;

import br.com.brunobrolesi.parking.controller.form.VehicleForm;


public class VehicleFormCreator {
    public static VehicleForm createVehicleForm() {
        VehicleForm vehicleForm = new VehicleForm();
        vehicleForm.setManufacturer(VehicleData.getManufacturer());
        vehicleForm.setModel(VehicleData.getModel());
        vehicleForm.setYear(VehicleData.getYear());
        vehicleForm.setColor(VehicleData.getColor());
        vehicleForm.setLicensePlate(VehicleData.getLicensePlate());
        vehicleForm.setType(VehicleData.getType().getId());

        return vehicleForm;
    }
}
