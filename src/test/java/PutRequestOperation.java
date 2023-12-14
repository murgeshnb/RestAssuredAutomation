import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class PutRequestOperation {
    private String propertyValue;
    @BeforeMethod
    public void bm(){
        propertyValue = PropertyUtility.getPropertyValue("base.url");
        RestAssured.baseURI=propertyValue;
    }

    @Test
    public void updateCartItems(){
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

        // Create cart
        Response cartCreationResponse = RestAssured.given()
                .header("authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .post("api/cart");

        String cartId = cartCreationResponse.jsonPath().getString("cart_id");

        // Add item to cart
        String initialQuantity = "15";
        String cartJsonBody = String.format("{\"product_id\":\"%s\", \"quantity\":\"%s\"}", productId, initialQuantity);

        Response addedItemResponse = RestAssured.given()
                .headers("authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(cartJsonBody)
                .post("api/cart/" + cartId + "/items");

        String cart_item_id = addedItemResponse.jsonPath().getString("cart_item_id");

        // update cart
        String quantityToUpdate = "15";
        String updateCartJsonBody = String.format("{\"product_id\":\"%s\", \"quantity\":\"%s\"}", productId, quantityToUpdate);
        System.out.println(updateCartJsonBody);

        Response updatedCartResponse = RestAssured.given()
                .header("authorization", "Bearer " + accessToken)
                .body(updateCartJsonBody)
                .contentType(ContentType.JSON)
                .put("api/cart/" + cartId + "/items/" + cart_item_id);

        String updatedCart_item_id = updatedCartResponse.jsonPath().getString("cart_item_id");
        String updatedCart_id = updatedCartResponse.jsonPath().getString("cart_id");
        String updatedQuantity = updatedCartResponse.jsonPath().getString("quantity");

        assertThat(cartId, Matchers.equalTo(updatedCart_id));
        assertThat(cart_item_id, Matchers.equalTo(updatedCart_item_id));
        assertThat(quantityToUpdate, Matchers.equalTo(updatedQuantity));


    }
}
