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
    protected static Properties PROPERTIES1;
            static {
                try {
                    fileInputStream = FileInputStream("src/test/resources/conf.properties");
                    PROPERTIES1 = new Properties();
                    PROPERTIES1.load(fileInputStream);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }

    public static String getProperty(String key) {
                return PROPERTIES.getProperty(key);
    }
    //нужно ли в проперти добавлять данные по сотруднику?
    public static String getProperty1(Integer id,String firstName, String lastName, String middleName, Integer companyId, String email, String url, String phone, String birthdate, Boolean isActive) {
        return PROPERTIES1.getProperty( id,firstName,lastName,middleName,companyId, email, url, phone, birthdate, isActive);
    }
}
