import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.PropertyUtility;
import utilities.RandomGenerator;

import static org.hamcrest.MatcherAssert.assertThat;

public class ExampleOnPartialUpdate {
    private String propertyValue;

    @BeforeMethod(groups = {"parallel"})
    public void bm() {
        propertyValue = PropertyUtility.getPropertyValue("base.url");
        RestAssured.baseURI = propertyValue;
    }

    @Test(groups = {"parallel"})
    public void updateCartItems() {
        String randomEmail = RandomGenerator.generateRandomEmail();
        String jsonBody = String.format("{\"email\":\"%s\", \"password\":\"123456\"}", randomEmail);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .post("api/auth/signup");

        String accessToken = response.jsonPath().getString("data.session.access_token");

        // profile creation
        String firstName = "Gelvi";
        String lastName = "Kalmi";
        String address = "IndiraNagar";
        String phoneNumber = "999999999";

        String profileCreationBody = String.format("{\"first_name\":\"%s\", \"last_name\":\"%s\", \"address\":\"%s\", \"mobile_number\":\"%s\"}", firstName, lastName, address, phoneNumber);

        Response profileCreationResponse = RestAssured.given()
                .headers("authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(profileCreationBody)
                .post("/api/profile");

        System.out.println(profileCreationResponse.statusCode());
        System.out.println(profileCreationResponse.jsonPath().getString("first_name"));

        // profile update
        firstName = "Mallamma";
        String profileUpdateBody = String.format("{\"first_name\":\"%s\", \"last_name\":\"%s\"}", firstName, lastName);

        Response profileUpdateResponse = RestAssured.given()
                .headers("authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(profileUpdateBody)
                .patch("/api/profile");

        assertThat(profileUpdateResponse.statusCode(), Matchers.is(200));

        String updatedName = profileUpdateResponse.jsonPath().getString("first_name");
        assertThat(updatedName, Matchers.equalTo(firstName));
        assertThat(profileUpdateResponse.jsonPath().getString("last_name"), Matchers.equalTo(lastName));
        assertThat(profileUpdateResponse.jsonPath().getString("address"), Matchers.equalTo(address));
        assertThat(profileUpdateResponse.jsonPath().getString("mobile_number"), Matchers.equalTo(phoneNumber));
        assertThat(profileUpdateResponse.jsonPath().getString("user_id"), Matchers.notNullValue());


    }
}
