package com.example.ui.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public final class FrameworkConfig {
    private static final String CONFIG_FILE = "/config.properties";
    private static final Properties PROPERTIES = loadProperties();

    private FrameworkConfig() {
    }

    public static String get(String key) {
        String value = System.getProperty(key);
        if (value == null || value.isBlank()) {
            value = PROPERTIES.getProperty(key);
        }
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing configuration property: " + key);
        }
        return value.trim();
    }

    public static int timeoutSeconds() {
        try {
            int timeout = Integer.parseInt(get("timeout.seconds"));
            if (timeout < 1) {
                throw new IllegalArgumentException("timeout.seconds must be greater than zero");
            }
            return timeout;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("timeout.seconds must be a valid integer", exception);
        }
    }

    public static boolean headless() {
        String value = get("headless");
        if ("true".equalsIgnoreCase(value)) {
            return true;
        }
        if ("false".equalsIgnoreCase(value)) {
            return false;
        }
        throw new IllegalArgumentException("headless must be either true or false");
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = FrameworkConfig.class.getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IllegalStateException("Could not find " + CONFIG_FILE + " on the classpath");
            }
            properties.load(input);
            return properties;
        } catch (IOException exception) {
            throw new UncheckedIOException("Could not load " + CONFIG_FILE, exception);
        }
    }
}
