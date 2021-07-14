package br.com.brunobrolesi.parking.util;


import br.com.brunobrolesi.parking.controller.form.UpdateParkingForm;

public class UpdateParkingFormCreator {
    public static UpdateParkingForm createUpdateParkingForm() {
        UpdateParkingForm updateParkingForm = new UpdateParkingForm();
        updateParkingForm.setName(ParkingData.getUpdatedName());
        updateParkingForm.setAddress(ParkingData.getUpdateAddressForm());
        updateParkingForm.setPhone1(ParkingData.getUpdatedPhone1());
        updateParkingForm.setPhone2(ParkingData.getPhone2());

        return updateParkingForm;
    }
}
