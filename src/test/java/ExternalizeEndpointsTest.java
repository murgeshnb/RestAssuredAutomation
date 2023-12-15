import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.EndPointConfig;
import utilities.PropertyUtility;
import utilities.RandomGenerator;

import java.util.Arrays;

public class ExternalizeEndpointsTest {

    private String propertyValue;
    @BeforeMethod
    public void bm(){
        propertyValue = PropertyUtility.getPropertyValue("base.url");
        RestAssured.baseURI=propertyValue;
    }

    @Test
    public void testExternalizeEndpoints(){
        String randomEmail = RandomGenerator.generateRandomEmail();
        String jsonBody = String.format("{\"email\":\"%s\", \"password\":\"123456\"}", randomEmail);

        String endPoint = EndPointConfig.getEndPoint("auth", "signUp");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .post(endPoint);

        String accessToken = response.jsonPath().getString("data.session.access_token");
        System.out.println(accessToken);
    }

}
