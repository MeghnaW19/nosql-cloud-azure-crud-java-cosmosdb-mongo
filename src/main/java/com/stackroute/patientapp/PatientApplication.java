package com.stackroute.patientapp;

import com.stackroute.patientapp.dao.PatientDao;
import com.stackroute.patientapp.dao.PatientDaoImpl;
import com.stackroute.patientapp.exception.PatientExistsException;
import com.stackroute.patientapp.model.Patient;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * This is the main application class used for executing
 * CRUD operations on the Database using methods defined in PatientDao
 * Log the results as informational messages
 */
@Slf4j
public class PatientApplication {
    public static void main(String[] args) {
        PatientDao patientDao = new PatientDaoImpl();

        //** TODO **
        // Add a new patient using the patientDao object
        Patient patientOne = new Patient(null, 101, "john", 'M', 25);
        Patient patient = null;
        try {
            patient = patientDao.addPatient(patientOne);
            log.info("New Patient created : {}", patient);
        } catch (PatientExistsException e) {
            log.error(e.getMessage());
        }

        //**TODO**
        // Get a patient given the patientCode
        int patientCode = 100;
        Optional<Patient> optionalPatient = patientDao.getPatient(patientCode);
        optionalPatient.ifPresentOrElse(
                foundPatient -> log.info("Patient found : {}", foundPatient),
                () -> log.info("Patient not found with patientCode : {}", patientCode)
        );

        //**TODO**
        // Update patient name given patientCode
        patientDao.updatePatient(101, "Jack");

        //**TODO**
        // Delete a Patient given patientCode
        patientDao.deletePatient(100);

        //**TODO**
        // Get all patients from the database
        patientDao.getAllPatients().forEach(p -> log.info("{}", p));
    }
}