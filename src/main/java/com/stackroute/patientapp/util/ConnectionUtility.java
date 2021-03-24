package com.stackroute.patientapp.util;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.stackroute.patientapp.model.Patient;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * This class is used for creating a Connection to Azure CosmosDB
 */
public class ConnectionUtility {

    private static final String connectionUrl;
    private static final String database;
    private static final String collection;
    private static MongoCollection<Patient> mongoCollection;

    static {
        connectionUrl = PropertyConfig.DB_PROPERTIES.getProperty("db.connection.url");
        database = PropertyConfig.DB_PROPERTIES.getProperty("db.name");
        collection = PropertyConfig.DB_PROPERTIES.getProperty("db.collection");
    }

    /**
     * **TODO**
     * This method should return a MongoCollection<Patient> based on the
     * configuration properties read from the application.properties file.
     * Configure the db.connection.url in application properties.
     * Use CodecRegistry for mapping Patient class
     */
    public static MongoCollection<Patient> getCollection() {
        if (mongoCollection != null) {
            return mongoCollection;
        }
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder()
                        .register(Patient.class).automatic(true)
                        .build()));
        MongoClientURI clientURI = new MongoClientURI(connectionUrl);
        MongoClient mongoClient = new MongoClient(clientURI);

        MongoDatabase patientDb = mongoClient.getDatabase(database);
        mongoCollection =
                patientDb.getCollection("patient", Patient.class)
                        .withCodecRegistry(pojoCodecRegistry);
        return mongoCollection;
    }

    /**
     * This method is used for dropping the collection
     */
    public static void dropCollection() {
        mongoCollection.drop();
    }
}
