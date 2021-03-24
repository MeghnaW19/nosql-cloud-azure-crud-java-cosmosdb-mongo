package com.stackroute.patientapp.dao;

import com.stackroute.patientapp.exception.PatientExistsException;
import com.stackroute.patientapp.model.Patient;

import java.util.List;
import java.util.Optional;

/**
 * This interface provides the CRUD operations to be implemented
 */
public interface PatientDao {

    Optional<Patient> getPatient(int patientId);

    Patient addPatient(Patient newPatient) throws PatientExistsException;

    boolean updatePatient(int patientCode, String name);

    boolean deletePatient(int patientCode);

    List<Patient> getAllPatients();
}