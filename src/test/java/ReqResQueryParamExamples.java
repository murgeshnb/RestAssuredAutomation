import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.PropertyUtility;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ReqResQueryParamExamples {
    private String propertyValue;

    @BeforeMethod
    public void bm(){
        propertyValue = PropertyUtility.getPropertyValue("reqres.url");
        RestAssured.baseURI=propertyValue;
    }

    @Test
    public void queryParamExamples(){

        // adding single qp
        Response response = RestAssured.given().queryParam("page", "2").get("api/users");
//        System.out.println(response.jsonPath().getString("."));

        //bdd style
        RestAssured.given().queryParam("page", "2")
                .when().get("api/users")
                .then().statusCode(200);

        // multiple
        Response response1 = RestAssured.given().queryParam("page", "1").queryParam("page", 2)
                .get("api/users");

//        System.out.println(response1.jsonPath().getString("."));

        // -bdd
        RestAssured.given().queryParam("page", 2)
                .when().get("api/users")
                .then().statusCode(200);

        // List
        List<String> list = Arrays.asList("1", "2");
        Response response2 = RestAssured.given().queryParam("page", list)
                .get("api/users");

//        System.out.println(response2.jsonPath().getString("."));

        // maps
        HashMap<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("page","2");

        Response response3 = RestAssured.given().queryParams(map)
                .get("api/users");
        System.out.println(response3.jsonPath().getString("."));
    }
}
