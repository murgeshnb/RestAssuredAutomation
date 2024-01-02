package builderPatterns;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.auth.SignUpRequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import models.auth.SignUpResponseModel;
import utilities.EndPointConfig;
import utilities.PropertyUtility;
import utilities.RandomGenerator;
import utilities.ResponseUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ResponseDeserializationTest {
    @BeforeMethod
    public void initialiseBaseURI(){
        String baseURI = PropertyUtility.getPropertyValue("base.url");
        RestAssured.baseURI = baseURI;
    }

    @Test
    public void testSignUpWithResponseDeserialization(){
        // Arrange
        String randomEmail = RandomGenerator.generateRandomEmail();
        String endPoint = EndPointConfig.getEndPoint("models/auth", "signUp");

        SignUpRequestModel requestModel = SignUpRequestModel.builder()
                .email(randomEmail)
                .password("123456")
                .build();

        // Act
        Response signUpResponse =
                RestAssured.given()
                        .contentType(ContentType.JSON)
                        .body(requestModel)
                        .post(endPoint);

        // Deserialize the response to SignUpResponseModel object
        SignUpResponseModel deserializedResponse = ResponseUtils.deserializeResponse(signUpResponse, SignUpResponseModel.class);

        // Assert
        assertEquals(signUpResponse.statusCode(), 201);
        assertEquals(deserializedResponse.getStatusCode(), 201);
        assertNotNull(deserializedResponse.getData());
        assertNotNull(deserializedResponse.getData().getSession().getAccessToken());
        System.out.println(signUpResponse.jsonPath().getString("data.session.access_token"));

    }

    @Test
    @Parameters({"username", "password"})
    public void hii(String username, String password) {
        System.out.println(username+" ,"+password);

    }
}
