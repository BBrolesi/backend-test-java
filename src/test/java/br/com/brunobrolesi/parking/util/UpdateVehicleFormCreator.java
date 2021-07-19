package br.com.brunobrolesi.parking.util;

import br.com.brunobrolesi.parking.controller.form.UpdateVehicleForm;

public class UpdateVehicleFormCreator {
    public static UpdateVehicleForm createVehicleForm() {
        UpdateVehicleForm vehicleForm = new UpdateVehicleForm();
        vehicleForm.setManufacturer(VehicleData.getManufacturer());
        vehicleForm.setModel(VehicleData.getModel());
        vehicleForm.setYear(VehicleData.getYear());
        vehicleForm.setColor(VehicleData.getColor());
        vehicleForm.setType(VehicleData.getType().getId());

        return vehicleForm;
    }
}
