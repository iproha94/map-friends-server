package com.wordpress.ilyaps.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {
    static final Logger LOGGER = LogManager.getLogger(PropertiesHelper.class);

    static public Properties getProperties(String fileName) {
        LOGGER.info("start getProperties by " + fileName);
        Properties properties = null;

        try (FileInputStream fis = new FileInputStream(fileName)){
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Error getProperties", e);
            System.out.close();
        }

        return properties;
    }
}
