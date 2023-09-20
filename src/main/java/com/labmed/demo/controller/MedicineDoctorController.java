package com.labmed.demo.controller;

import com.labmed.demo.dto.MedicineDoctorDto;
import com.labmed.demo.model.MedicineDoctorModel;
import com.labmed.demo.repository.MedicineDoctorRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class MedicineDoctorController {

    @Autowired
    MedicineDoctorRepository medicineDoctorRepository;

    static final String MESSAGE_ERROR_INVALID_DATA = "Invalid data";

    @PostMapping("doctors")
    public ResponseEntity<MedicineDoctorModel> saveDoctor(
            @RequestBody @Valid MedicineDoctorDto doctorDto){

        MedicineDoctorModel doctorModel = new MedicineDoctorModel();

        BeanUtils.copyProperties(doctorDto, doctorModel);

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(medicineDoctorRepository.save(doctorModel));
        }
        catch (ConstraintViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, MESSAGE_ERROR_INVALID_DATA, ex);
        }
    }

    @GetMapping("doctors")
    public ResponseEntity<List<MedicineDoctorModel>> listDoctors(){

        return ResponseEntity.status(HttpStatus.OK).body(medicineDoctorRepository.findAll());
    }

    @GetMapping("doctors/{id}")
    public ResponseEntity<MedicineDoctorDto>getOneDoctor(@PathVariable(name = "id") UUID id){
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(medicineDoctorRepository.findById(id).map(MedicineDoctorDto::new).orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                    )
                );
    }

    @PutMapping("doctors/{id}")
    public ResponseEntity<MedicineDoctorModel> replaceUserData(@PathVariable(value = "id") UUID id,
                                                             @RequestBody @Valid MedicineDoctorDto doctorDto ) {

        medicineDoctorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        MedicineDoctorModel doctorModel = new MedicineDoctorModel();
        BeanUtils.copyProperties(doctorDto, doctorModel);
        doctorModel.setId(id);

        try {
            return ResponseEntity.status(HttpStatus.OK).body(medicineDoctorRepository.save(doctorModel));

        } catch (ConstraintViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, MESSAGE_ERROR_INVALID_DATA, ex);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("doctors/{id}")
    public void deleteDoctor(@PathVariable UUID id) {
        try {
            medicineDoctorRepository.deleteById(id);
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found", ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient cannot be deleted", ex);
        }
    }
}
