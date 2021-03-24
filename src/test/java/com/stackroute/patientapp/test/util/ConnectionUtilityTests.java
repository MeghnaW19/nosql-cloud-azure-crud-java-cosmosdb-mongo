package com.stackroute.patientapp.test.util;

import com.mongodb.client.MongoCollection;
import com.stackroute.patientapp.model.Patient;
import com.stackroute.patientapp.util.ConnectionUtility;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ConnectionUtilityTests {

    private MongoCollection<Patient> mongoCollection;

    @Test
    public void givenConnectionStringThenReturnMongoCollection() {
        mongoCollection = ConnectionUtility.getCollection();
        assertThat(mongoCollection, is(notNullValue()));
    }
}