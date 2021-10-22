package utilities;

import java.io.IOException;
import java.util.Properties;

public class PropertyManager {

    private static PropertyManager instance;
    private static final Object lock = new Object();
    private static String baseUrl;
    private static String email;
    private static String password;

    public static void main(String[] args) {

        PropertyManager app = new PropertyManager();
        Properties merged = app.loadPropertiesFile();
        merged.forEach((k, v) -> System.out.println(k + ":" + v));

    }

    public static PropertyManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                instance = new PropertyManager();
                instance.loadPropertiesFile();
            }
        }
        return instance;
    }

    public Properties loadPropertiesFile() {

        Properties prop = new Properties();

        try {
            prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            System.err.println("Configuration properties file cannot be found");
        }
        baseUrl = prop.getProperty("baseUrl");
        email = prop.getProperty("email");
        password = prop.getProperty("password");
        return prop;
    }

    public String getbaseUrl() {
        return baseUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
