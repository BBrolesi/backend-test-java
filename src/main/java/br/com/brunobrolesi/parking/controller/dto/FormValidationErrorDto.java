package br.com.brunobrolesi.parking.controller.dto;

public class FormValidationErrorDto {

    private String campo;
    private String error;

    public FormValidationErrorDto(String campo, String error) {
        this.campo = campo;
        this.error = error;
    }

    public String getCampo() {
        return campo;
    }

    public String getError() {
        return error;
    }
}
