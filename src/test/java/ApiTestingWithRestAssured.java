import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class ApiTestingWithRestAssured {
    public String propertyValue;

    @BeforeMethod
    public void bm(){
        propertyValue = PropertyUtility.getPropertyValue("base.url");
        RestAssured.baseURI=propertyValue;
    }

    @Test
    public void fetchAndLogApiStatusCode(){

        Response response = given().get();

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        System.out.println(response.getBody().asString());
    }

    @Test
    public void createUser(){
        String randomEmail = RandomGenerator.generateRandomEmail();
        String jsonBody = String.format("{\"email\":\"%s\", \"password\":\"123456\"}", randomEmail);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("api/auth/signup");
        String body = response.getBody().asString();
        String s = formatJson(body);
        System.out.println(s);

        assertThat(response.getStatusCode(), Matchers.is(201));
        assertThat(response.jsonPath().get("data"), Matchers.notNullValue());
        assertThat(response.jsonPath().get("data.user"), Matchers.notNullValue());
        assertThat(response.jsonPath().get("data.session"), Matchers.notNullValue());

    }

    @Test
    public void bddApproach(){
        String payload = "{\"email\":\"wer23@gmail.com\", \"password\":\"123456\"}";
        given()
                .baseUri("https://www.apicademy.dev/")
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post()
                .then()
                .statusCode(201);
    }

    public static String formatJson(String jsonString){
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject asJsonObject = jsonParser.parse(jsonString).getAsJsonObject();
//        assertThat(asJsonObject, Matchers.hasProperty("data.user.aud", Matchers.equalTo("authenticated")));
        return gson.toJson(asJsonObject);
    }

    @Test
    public void hamcrestAssertions(){
        //Basic Matchers
        assertThat("Example", equalToIgnoringCase("example"));
        assertThat("Hello World", containsString("World"));

        // Numeric matchers
        assertThat(5, Matchers.greaterThan(4));
        assertThat(10, Matchers.lessThanOrEqualTo(10));

        assertThat("hello", Matchers.notNullValue());

        // Logical matchers
        assertThat(7, Matchers.anyOf(Matchers.greaterThan(8), Matchers.lessThanOrEqualTo(7)));
        assertThat(4, allOf(Matchers.greaterThan(3), Matchers.lessThan(6)));

        // Collection matchers - hasItem, hasItems, everyItem
        assertThat(Arrays.asList(1, 2, 3), Matchers.hasItem(2));
        assertThat(Arrays.asList(4, 5, 7), Matchers.hasItems(5, 7));
        assertThat(Arrays.asList(2, 6,8), Matchers.everyItem(Matchers.greaterThan(1)));

        // Object matchers - instanceOf, hasProperty
        assertThat("Hello world", Matchers.instanceOf(String.class));

        // Combining matchers
        assertThat("hello world", allOf(containsString("hello"), not("bye")));

    }

    @Test
    public void assertionsPart2(){

        String propertyValue1 = PropertyUtility.getPropertyValue("reqres.url");
        RestAssured.baseURI=propertyValue1;

        Response response = given().get("api/users/2");
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        String responseInString = response.getBody().asString();
        JsonObject asJsonObject = jsonParser.parse(responseInString).getAsJsonObject();
        given().when().get("api/users/2").then().body("data", hasKey("id"));
        System.out.println(response.getBody().asString());

    }

}
