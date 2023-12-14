import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ValidateAPIResponseAttributesTest {
    private String propertyValue;
    @BeforeMethod
    public void bm(){
        propertyValue = PropertyUtility.getPropertyValue("base.url");
        RestAssured.baseURI=propertyValue;
    }

    @Test
    public void validateResponseHeaders(){
        String randomEmail = RandomGenerator.generateRandomEmail();
        String jsonBody = String.format("{\"email\":\"%s\", \"password\":\"123456\"}", randomEmail);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/api/auth/signup");

        String accessToken = response.jsonPath().getString("data.session.access_token");
        System.out.println(accessToken);

        Response productResponse = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .get("api/products");

        Headers responseHeaders = productResponse.getHeaders();
        long time = productResponse.getTime();

        MatcherAssert.assertThat(time, Matchers.lessThan(3000L));
        MatcherAssert.assertThat(responseHeaders.getValue("Content-Type"), Matchers.equalTo("application/json; charset=utf-8"));

        //validate status and status text
        MatcherAssert.assertThat(productResponse.getStatusCode(), Matchers.is(200));
        MatcherAssert.assertThat(productResponse.getStatusLine(), Matchers.containsString("OK"));

        //get cookie
//        String cookieValue = productResponse.getCookie("cookieName");
//        System.out.println(cookieValue);
//        Assert.assertEquals("cookieValue", cookieValue);

    }
}
