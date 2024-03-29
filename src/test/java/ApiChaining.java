import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.PropertyUtility;
import utilities.RandomGenerator;

import static org.hamcrest.MatcherAssert.assertThat;

public class ApiChaining {
    private String propertyValue;
    @BeforeMethod(groups = {"parallel"})
    public void bm(){
        propertyValue = PropertyUtility.getPropertyValue("base.url");
        RestAssured.baseURI=propertyValue;
    }

    @Test(groups = {"parallel"})
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


        assertThat(productsResponse.getStatusCode(), Matchers.is(200));
        assertThat(productCount, Matchers.equalTo(2));
    }

    @Test
    public void bddStyle(){
        RestAssured.given().queryParam("page", 2)
                .when().get("/api/auth/signup")
                .then().statusCode(200);
    }
}
