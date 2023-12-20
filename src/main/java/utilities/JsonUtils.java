package utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JsonUtils {
    public static Map<String, String> CONFIGMAP;

    private JsonUtils(){

    }

    static {
        try{
            CONFIGMAP = new ObjectMapper().readValue(new File("data" + "/" + "endpoints" + ".json"),
                    new TypeReference<HashMap<String, String>>() {});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String get(ConfigProperties key){
        if (Objects.isNull(CONFIGMAP.get(key.name().toLowerCase()))){
            throw new RuntimeException("Property name "+key+" is not found");
        }
        return CONFIGMAP.get(key.name().toLowerCase());
    }
}
