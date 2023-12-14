import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.Header;
import org.apache.http.auth.AUTH;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ApiChaining {
    private String propertyValue;
    @BeforeMethod
    public void bm(){
        propertyValue = PropertyUtility.getPropertyValue("base.url");
        RestAssured.baseURI=propertyValue;
    }

    @Test
    public void getAccessToken(){
        String randomEmail = RandomGenerator.generateRandomEmail();
        String jsonBody = String.format("{\"email\":\"%s\", \"password\":\"123456\"}", randomEmail);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/api/auth/signup");

        String accessToken = response.jsonPath().getString("data.session.access_token");
        System.out.println(accessToken);

        Response productsResponse = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("page", 1)
                .queryParam("limit", 2)
                .get("/api/products");

        JsonPath jsonPath = productsResponse.jsonPath();
        int productCount = jsonPath.getList("$").size();
        System.out.println("response: "+response.jsonPath().getString("."));


        MatcherAssert.assertThat(productsResponse.getStatusCode(), Matchers.is(200));
        MatcherAssert.assertThat(productCount, Matchers.equalTo(2));
    }

    @Test
    public void bddStyle(){
        RestAssured.given().queryParam("page", 2)
                .when().get("/api/auth/signup")
                .then().statusCode(200);
    }
}
