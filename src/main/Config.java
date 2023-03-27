package main;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class Config {
    
    private static final String FILE_NAME = "src/main/config.properties";
    
    private static Properties properties;
    
    static {
        properties = new Properties();
        try {
            FileInputStream file = new FileInputStream(FILE_NAME);
            properties.load(file);
            file.close();
        } catch (IOException e) {
            System.out.println("Could not load properties file");
        }
    }
    
    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
        try {
            FileOutputStream file = new FileOutputStream(FILE_NAME);
            properties.store(file, null);
            file.close();
        } catch (IOException e) {
            System.out.println("Could not save properties file");
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public static int getHexIntProperty(String key) {
        return Integer.parseInt(properties.getProperty(key), 16);
    }
}
