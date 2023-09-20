package com.labmed.demo.repository;

import com.labmed.demo.model.MedicineDoctorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicineDoctorRepository extends JpaRepository<MedicineDoctorModel, UUID> {
}
