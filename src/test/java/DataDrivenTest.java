import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.*;

import static org.testng.Assert.assertEquals;

public class DataDrivenTest {

    private String propertyValue;
    @BeforeMethod
    public void bm(){
        propertyValue = PropertyUtility.getPropertyValue("base.url");
        RestAssured.baseURI=propertyValue;
    }

    @Test
    public void dataDrivenUpdateProfileTest(){
        String randomEmail = RandomGenerator.generateRandomEmail();
        String jsonBody = String.format("{\"email\":\"%s\", \"password\":\"123456\"}", randomEmail);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .post("api/auth/signup");

        String accessToken = response.jsonPath().getString("data.session.access_token");

        // profile creation
        String firstName = DataLoader.getData("profileData", "first_name");
        String lastName = DataLoader.getData("profileData", "last_name");
        String address = DataLoader.getData("profileData", "address");
        String phoneNumber = DataLoader.getData("profileData", "mobile_number");

        String profileCreationBody = String.format("{\"first_name\":\"%s\", \"last_name\":\"%s\", \"address\":\"%s\", \"mobile_number\":\"%s\"}", firstName, lastName, address, phoneNumber);

        Response profileCreationResponse = RestAssured.given()
                .headers("authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(profileCreationBody)
                .post("/api/profile");

        System.out.println(profileCreationResponse.statusCode());
        System.out.println(profileCreationResponse.jsonPath().getString("first_name"));

        assertEquals(profileCreationResponse.getStatusCode(), 201);
        assertEquals(profileCreationResponse.jsonPath().getString("first_name"), firstName);
        assertEquals(profileCreationResponse.jsonPath().getString("last_name"), lastName);
        assertEquals(profileCreationResponse.jsonPath().getString("address"), address);
        assertEquals(profileCreationResponse.jsonPath().getString("mobile_number"), phoneNumber);

        // profile update
        firstName = "Kane";
        lastName = "Jenny";
        String profileUpdateBody = String.format("{\"first_name\":\"%s\", \"last_name\":\"%s\"}", firstName, lastName);

        Response profileUpdateResponse = RestAssured.given()
                .headers("authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(profileUpdateBody)
                .patch("/api/profile");

        assertEquals(profileUpdateResponse.getStatusCode(), 200);
        assertEquals(profileUpdateResponse.jsonPath().getString("first_name"), firstName);
        assertEquals(profileUpdateResponse.jsonPath().getString("last_name"), lastName);
    }

    @Test
    public void readJsonData(){
        String value = JsonUtils.get(ConfigProperties.AUTH);
        System.out.println(value);
    }

}
