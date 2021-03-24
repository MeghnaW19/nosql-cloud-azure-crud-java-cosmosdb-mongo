package com.stackroute.patientapp.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

/**
 * This class is used for loading the properties defined in application.properties file
 */
@Slf4j
public class PropertyConfig {

    public static final Properties DB_PROPERTIES = new Properties();

    static {
        try {
            DB_PROPERTIES.load(PropertyConfig.class
                    .getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException exception) {
            log.error("Error loading application properties : {}", exception.getMessage());
        }
    }
}