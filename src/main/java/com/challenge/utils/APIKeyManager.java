package com.challenge.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * A singleton class to manage the API key used for accessing external services.
 */
public class APIKeyManager {
    private static final APIKeyManager instance = new APIKeyManager();
    private final String apiKey;

    /**
     * Private constructor to initialize the API key by loading it from a properties file.
     */
    private APIKeyManager() {
        this.apiKey = loadApiKey();
    }

    /**
     * Retrieves the singleton instance of the APIKeyManager.
     *
     * @return The singleton instance of APIKeyManager.
     */
    public static APIKeyManager getInstance() {
        return instance;
    }

    /**
     * Loads the API key from an external properties file.
     *
     * @return The API key loaded from the properties file.
     * @throws RuntimeException if an error occurs while loading the API key.
     */
    private String loadApiKey() {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader("application.properties"));
            return properties.getProperty("API_KEY");
        } catch (IOException e) {
            throw new RuntimeException("Error loading the API key", e);
        }
    }

    /**
     * Retrieves the API key.
     *
     * @return The API key stored in the APIKeyManager.
     */
    public String getApiKey() {
        return apiKey;
    }
}