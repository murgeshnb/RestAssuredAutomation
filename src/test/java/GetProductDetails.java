import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GetProductDetails {
    private String propertyValue;
    @BeforeMethod
    public void bm(){
        propertyValue = PropertyUtility.getPropertyValue("base.url");
        RestAssured.baseURI=propertyValue;
    }

    @Test
    public void getProductDetails(){
        String randomEmail = RandomGenerator.generateRandomEmail();
        String jsonBody = String.format("{\"email\":\"%s\", \"password\":\"123456\"}", randomEmail);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .post("api/auth/signup");

        String accessToken = response.jsonPath().getString("data.session.access_token");

        // get all products
        Response productResponse = RestAssured.given()
                .header("authorization", "Bearer "+accessToken)
                .get("api/products");

        String productId = productResponse.jsonPath().getString("[0].id");
        System.out.println(productId);

        //get ind product details
        Response productDetailsResponse = RestAssured.given()
                .header("authorization", "Bearer "+accessToken)
                .get("api/products/" + productId);

        System.out.println(productDetailsResponse.jsonPath().getString("id"));
        MatcherAssert.assertThat(productId, Matchers.equalTo(productDetailsResponse.jsonPath().getString("id")));

    }


    @Test
    public void cartOperations(){
        String randomEmail = RandomGenerator.generateRandomEmail();
        String jsonBody = String.format("{\"email\":\"%s\", \"password\":\"123456\"}", randomEmail);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .post("api/auth/signup");

        String accessToken = response.jsonPath().getString("data.session.access_token");
        System.out.println(accessToken);

        // Cart creation
        Response cartResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("authorization", "Bearer " + accessToken)
                .post("/api/cart");
        String cartId = cartResponse.jsonPath().getString("cart_id");

        // Cart deletion
        Response cartDeletedResponse=null;

        //Idempotent cart deletion
        for (int i=0; i<5; i++){
            cartDeletedResponse = RestAssured.given()
                    .header("authorization", "Bearer " + accessToken)
                    .delete("/api/cart/" + cartId);
        }

        System.out.println(cartDeletedResponse.statusCode());
        MatcherAssert.assertThat(cartDeletedResponse.statusCode(), Matchers.is(204));

        // Attempt to retrieve the deleted cart
        Response cartRetrievalResponse = RestAssured.given()
                .header("authorization", "Bearer " + accessToken)
                .get("api/cart");

        // validate cart retrieval response
        MatcherAssert.assertThat(cartRetrievalResponse.statusCode(), Matchers.is(200));
        MatcherAssert.assertThat(cartRetrievalResponse.jsonPath().getString("message"), Matchers.equalTo("No cart found"));


    }
}
