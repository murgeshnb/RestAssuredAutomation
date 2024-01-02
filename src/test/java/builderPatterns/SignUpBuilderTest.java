package builderPatterns;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.auth.SignUpRequestModel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.EndPointConfig;
import utilities.PropertyUtility;
import utilities.RandomGenerator;

public class SignUpBuilderTest {
    private String propertyValue;
    @BeforeMethod
    public void bm(){
        propertyValue = PropertyUtility.getPropertyValue("base.url");
        RestAssured.baseURI=propertyValue;
    }

    @Test
    public void dataDrivenUpdateProfileTest() {
        String randomEmail = RandomGenerator.generateRandomEmail();

        SignUpRequestModel signupRequestBody = SignUpRequestModel.builder()
                .email(randomEmail)
                .password("123456")
                .build();

        String signUpEndPoint = EndPointConfig.getEndPoint("models/auth", "signUp");

        Response signupResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(signupRequestBody)
                .post(signUpEndPoint);

        String accessToken = signupResponse.jsonPath().getString("data.session.access_token");
        System.out.println(accessToken);
        Assert.assertEquals(signupResponse.statusCode(), 201, "status code not expected");

    }
}
