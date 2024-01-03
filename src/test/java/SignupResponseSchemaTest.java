import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.auth.SignUpRequestModel;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.EndPointConfig;
import utilities.PropertyUtility;
import utilities.RandomGenerator;
import utilities.SchemaValidator;

public class SignupResponseSchemaTest {
    @Test
    public void validateSchema(){
        String baseUrl = PropertyUtility.getPropertyValue("base.url");
        RestAssured.baseURI=baseUrl;
        String randomEmail = RandomGenerator.generateRandomEmail();

        String endPoint = EndPointConfig.getEndPoint("auth", "signUp");

        SignUpRequestModel signUpRequestModel = SignUpRequestModel.builder().email(randomEmail).password("123456").build();

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(signUpRequestModel)
                .post(endPoint);

        System.out.println(response.statusCode());
        Assert.assertEquals(response.statusCode(), 201, "status code mismatch");

        String schemaPath = "/schemas/SignUpResponseSchema.json";

        // validate json schema
        boolean isSchemaValid = SchemaValidator.validateSchema(response, schemaPath);
        Assert.assertTrue(isSchemaValid, "schema validation failed");
    }
}
