package service;

import org.yaml.snakeyaml.introspector.Property;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfHelper {

    protected static FileInputStream fileInputStream;

    protected static Properties PROPERTIES;
    // используем файл conf.properties

            static {
                try {
                    fileInputStream = new FileInputStream("src/test/resources/conf.properties");
                    PROPERTIES = new Properties();
                    PROPERTIES.load(fileInputStream);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

    public static String getProperty(String key) {
                return PROPERTIES.getProperty(key);
    }

}
