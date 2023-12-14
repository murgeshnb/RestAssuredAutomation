import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

public class EnhancedUserSignupTest {
    public String propertyValue;

    @BeforeMethod
    public void bm(){
        propertyValue = PropertyUtility.getPropertyValue("base.url");
        RestAssured.baseURI=propertyValue;
    }

    @Test
    public void userSignUp(){
        String randomEmail = RandomGenerator.generateRandomEmail();
        String jsonBody = String.format("{\"email\":\"%s\", \"password\":\"123456\"}", randomEmail);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("api/auth/signup");

        assertThat(response.getStatusCode(), Matchers.equalTo(201));
        assertThat(response.jsonPath().getString("data.user.email"), Matchers.equalTo(randomEmail));
        assertThat(response.jsonPath().getString("data.session.token_type"), Matchers.equalTo("bearer"));
        assertThat(response.jsonPath().getString("data.session.refresh_token"), Matchers.notNullValue());
        assertThat(response.jsonPath().getString("data.user.id"), Matchers.equalTo(response.jsonPath().getString("data.session.user.id")));
        assertThat(response.jsonPath().getList("data.user.app_metadata.providers"), Matchers.contains("email"));
        assertThat(response.jsonPath().getString("data.user.aud"), Matchers.equalTo("authenticated"));
        assertThat(response.jsonPath().getString("data.user.role"), Matchers.equalTo("authenticated"));
        assertThat(response.jsonPath().getString("data.user.created_at"), Matchers.matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*"));
        assertThat(response.jsonPath().getString("data.user.created_at"), Matchers.matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*"));
        assertThat(response.jsonPath().getString("data.user.updated_at"), Matchers.matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*"));

    }

    @Test
    public void examplesOnRegexPatterns(){
        String word = "abc1234ABC";
//        assertThat("abc1234", Matchers.matchesPattern("[a-z0-9A-Z]+"));
        assertThat("TEst1234Vagrant", Matchers.matchesPattern("[A-Z]{2}[a-z]+[0-4]+[A-Z][a-z]+"));
    }

    @Test
    public void testGetProductsWithoutAuthHeader(){
        Response response = given().get("/api/products/");

        assertThat(response.getStatusCode(), Matchers.is(400));
        assertThat(response.jsonPath().getString("message"), Matchers.equalTo("Authorization header is missing."));
    }
}
