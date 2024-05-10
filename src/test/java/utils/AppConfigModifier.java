package utils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class AppConfigModifier {
    private final String configFileName = "application.properties";
    private final File configFile;

    public AppConfigModifier() {
        ClassLoader classLoader = getClass().getClassLoader();
        this.configFile = new File(Objects.requireNonNull(classLoader.getResource(configFileName)).getFile());
    }

    public synchronized void modifyConfigForTesting(Map<String, Boolean> propertiesToUpdate) throws IOException {
        Properties properties = loadProperties();
        updateProperties(properties, propertiesToUpdate);
        saveProperties(properties);
        System.out.println(propertiesToUpdate);
        System.out.println("Config updated" + properties);
    }

    private Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(configFile)) {
            properties.load(inputStream);
        }
        return properties;
    }

    private void updateProperties(Properties properties, Map<String, Boolean> propertiesToUpdate) {
        for (Map.Entry<String, Boolean> entry : propertiesToUpdate.entrySet()) {
            properties.setProperty(entry.getKey(), String.valueOf(entry.getValue()));
        }
    }

    private void saveProperties(Properties properties) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(configFile);
             FileChannel channel = outputStream.getChannel();
             FileLock lock = channel.lock()) {
            properties.store(outputStream, null);
        }
    }

    public synchronized void restoreConfig() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("fileSize", "false");
        properties.setProperty("fileHash", "false");
        saveProperties(properties);
    }
}
