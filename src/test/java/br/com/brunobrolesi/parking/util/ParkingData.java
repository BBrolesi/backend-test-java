package br.com.brunobrolesi.parking.util;

import br.com.brunobrolesi.parking.controller.form.AddressForm;
import br.com.brunobrolesi.parking.controller.form.UpdateAddressForm;

public class ParkingData {

    static public String getCnpj() {
        return "555.555.555";
    }

    static public String getName() {
        return "Estacionamento Central";
    }

    static public String getUpdatedName() {
        return "Estacionamento Morumbi";
    }

    static public AddressForm getAddressForm() {
        AddressForm addressForm = new AddressForm();
        addressForm.setStreet("Avenida Brasil");
        addressForm.setNumber("333");
        addressForm.setAddress_2(null);
        addressForm.setCityId(1);
        return addressForm;
    }

    static public UpdateAddressForm getUpdateAddressForm() {
        UpdateAddressForm updateAddressForm = new UpdateAddressForm();
        updateAddressForm.setStreet("Avenida Taguatingueta");
        updateAddressForm.setNumber("1233");
        updateAddressForm.setAddress_2(null);
        return updateAddressForm;
    }

    static public String getStreet() {
        return "Avenida Brasil";
    }

    static public String getNumber() {
        return "333";
    }

    static public String getAddress_2() {
        return null;
    }

    static public Integer getCityId() {
        return 1;
    }

    static public String getPhone1() {
        return "(11) 99999-0000";
    }

    static public String getUpdatedPhone1() {
        return "(11) 99229-0000";
    }

    static public String getPhone2() {
        return "(11) 98888-9999";
    }

    static public Integer getCarSpaces() {
        return 4;
    }

    static public Integer getMotorcycleSpaces() {
        return 4;
    }
}
