package com.stackroute.patientapp.commander.dao;

import com.stackroute.patientapp.dao.PatientDaoImpl;
import com.stackroute.patientapp.exception.PatientExistsException;
import com.stackroute.patientapp.model.Patient;
import com.stackroute.patientapp.util.ConnectionUtility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PatientDaoImplTests {

    private PatientDaoImpl patientDao;
    private Patient patient;
    private Patient addedPatient;

    @BeforeAll
    public static void beforeAll() {
        ConnectionUtility.getCollection();
        ConnectionUtility.dropCollection();
    }

    @BeforeEach
    public void setUp() throws PatientExistsException {
        patientDao = new PatientDaoImpl();
        patient = new Patient(null, 200, "mike", 'M', 25);
        addedPatient = patientDao.addPatient(patient);
    }

    @AfterEach
    public void tearDown() {
        ConnectionUtility.dropCollection();
    }

    @Test
    public void givenPatientObjectWhenSavedToDbThenReturnPatientObjectCreated() throws PatientExistsException {
        assertThat(addedPatient.getId(), notNullValue());
    }

    @Test
    public void givenPatientObjectWhenPatientExistsInDbThenThrowException() throws PatientExistsException {
        assertThrows(PatientExistsException.class, () -> {
            patientDao.addPatient(patient);
        });
    }

    @Test
    public void givenPatientCodeWhenPatientExistsInDbThenReturnOptionalOfPatient() {
        Optional<Patient> existingPatient = patientDao.getPatient(200);
        assertThat(existingPatient.isEmpty(), is(false));
        assertThat(existingPatient.get().getName(), is("mike"));
    }

    @Test
    public void givenPatientCodeWhenPatientNotExistsInDbThenReturnEmptyOptional() {
        assertThat(patientDao.getPatient(999), is(Optional.empty()));
    }

    @Test
    public void givenPatientCodeAndNameWhenUpdatedInDbThenReturnTrue() {
        boolean updated = patientDao.updatePatient(200, "michael");
        assertThat(updated, is(true));
        Optional<Patient> updatedPatient = patientDao.getPatient(200);
        assertFalse(updatedPatient.isEmpty());
        assertThat(updatedPatient.get().getName(), is("michael"));

    }

    @Test
    public void givenPatientCodeWhenDeletedInDbThenReturnTrue() {
        boolean deleted = patientDao.deletePatient(200);
        assertThat(deleted, is(true));
        assertThat(patientDao.getPatient(200), is(Optional.empty()));
    }

    @Test
    public void testGetAllPatients() throws PatientExistsException {
        patientDao.addPatient(new Patient(null, 201, "mary", 'F', 22));
        List<Patient> allPatients = patientDao.getAllPatients();
        assertThat(allPatients, hasSize(2));
        patientDao.deletePatient(201);
    }
}