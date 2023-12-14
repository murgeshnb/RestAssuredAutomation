import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class JSONExamples {
    @Test
    public void jsonExamples(){
        JSONObject publisher = new JSONObject();
        publisher.put("name", "ABC");
        publisher.put("location", "ABC");

        JSONArray array = new JSONArray();
        array.put("Hi").put("Hello").put("How ").put("are");

//        System.out.println(array);
//        System.out.println(publisher.toString(1));

        String jsonString = "{\"name\":\"A\", \"age\":\"30\", \"city\":\"bengaluru\"}";
        JSONObject jsonObject = new JSONObject(jsonString);
        System.out.println(jsonObject.toString(1));
        System.out.println(jsonObject.getString("name"));
    }
}