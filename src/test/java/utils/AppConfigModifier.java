package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class AppConfigModifier {
    private final String configFilePath;

    public AppConfigModifier() {
        this.configFilePath = "application.properties";
    }

    public AppConfigModifier(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    public void modifyConfigForTesting(Map<String, Boolean> configValues) {
        try {
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFilePath);
            properties.load(inputStream);

            // Modify the values
            for (Map.Entry<String, Boolean> entry : configValues.entrySet()) {
                properties.setProperty(entry.getKey(), entry.getValue().toString());
            }

            // Save the changes
            FileOutputStream outputStream = new FileOutputStream(configFilePath);
            properties.store(outputStream, null);

            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
