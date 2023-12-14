import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtility {
    public static String getPropertyValue(String key) {
        String configFilePath = "src/main/resources/config.properties";
        Properties properties = new Properties();
        try {
            FileInputStream inputStream = new FileInputStream(configFilePath);
            properties.load(inputStream);
            return properties.getProperty(key);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
