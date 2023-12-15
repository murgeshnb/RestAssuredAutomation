package utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class EndPointConfig {
    public static final String CONFIG_FILE = "endpoints.json";
    public static JsonNode jsonNode;

    static {
        try(InputStream inputStream = EndPointConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonNode = objectMapper.readTree(inputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getEndPoint(String key, String property){
        JsonNode endPointNode = jsonNode.path(key).path(property);
        return endPointNode.asText();
    }
}
