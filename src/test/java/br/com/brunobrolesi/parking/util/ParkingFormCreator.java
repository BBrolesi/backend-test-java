package br.com.brunobrolesi.parking.util;

import br.com.brunobrolesi.parking.controller.form.AddressForm;
import br.com.brunobrolesi.parking.controller.form.ParkingForm;

public class ParkingFormCreator {
    public static ParkingForm createParkingForm() {
        ParkingForm parkingForm = new ParkingForm();
        parkingForm.setCnpj(ParkingData.getCnpj());
        parkingForm.setName(ParkingData.getName());
        parkingForm.setAddress(ParkingData.getAddressForm());
        parkingForm.setPhone1(ParkingData.getPhone1());
        parkingForm.setPhone2(ParkingData.getPhone2());
        parkingForm.setCarSpaces(ParkingData.getCarSpaces());
        parkingForm.setMotorcycleSpaces(ParkingData.getMotorcycleSpaces());

        return parkingForm;
    }
}
