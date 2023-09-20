package com.labmed.demo.dto;

import com.labmed.demo.model.MedicineDoctorModel;
import jakarta.validation.constraints.NotEmpty;

public record MedicineDoctorDto(

        @NotEmpty(message = "The field NAME is mandatory.")
        String name,

        @NotEmpty(message = "The field E-MAIL is mandatory.")
        String email,

        @NotEmpty(message = "The field Phone Number is mandatory.")
        String phoneNumb,

        @NotEmpty(message = "The field CRM is mandatory.")
        String crm) {
    public MedicineDoctorDto(MedicineDoctorModel medicineDoctorModel){
        this(
                medicineDoctorModel.getName(),
                medicineDoctorModel.getEmail(),
                medicineDoctorModel.getPhoneNumb(),
                medicineDoctorModel.getCrm()
        );
    }

}
