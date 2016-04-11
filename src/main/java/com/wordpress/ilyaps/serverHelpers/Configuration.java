package com.wordpress.ilyaps.serverHelpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by stalker on 01.11.15.
 */
public final class Configuration {
    static final Logger LOGGER = LogManager.getLogger(Configuration.class);
    private final Properties properties = new Properties();


    public Configuration(List<String> listImportantProperies, String propertiesFile) {

        try (final FileInputStream fis = new FileInputStream(propertiesFile)) {
            properties.load(fis);
        } catch (IOException ignored) {
            LOGGER.error("IOException read the file of properties");
            throw new RuntimeException();
        }

        for (String property: listImportantProperies) {
            String value = properties.getProperty(property);

            if (value == null) {
                LOGGER.error("пустое свойство " + property);
                throw new NullPointerException();
            }
            LOGGER.info(property + "  = " + value);
        }
    }

    public String getValueOfProperty( String property) {
        String value = properties.getProperty(property);
        if (value == null) {
            LOGGER.error(property + " == null");
            throw new NullPointerException();
        }
        return value;
    }
}