package clients;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.auth.cart.AddItemToCartRequestModel;
import models.auth.cart.AddItemToCartResponseModel;
import models.auth.cart.CreateCartResponseModel;
import utilities.EndPointConfig;
import utilities.ResponseUtils;

public class CartClient {
    public static CreateCartResponseModel createCart(String accessToken){
        String endPoint = EndPointConfig.getEndPoint("cart", "createCart");
        System.out.println(endPoint);

        Response crateCartResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .headers("authorization", "Bearer " + accessToken)
                .post(endPoint);

        return ResponseUtils.deserializeResponse(crateCartResponse, CreateCartResponseModel.class);
    }

    public static AddItemToCartResponseModel addItemToCart(String accessToken, String cartId, String productId, int quantity) {
        String endPoint = EndPointConfig.getEndPoint("cart", "addCartItem");
        String replacedEndPoint = endPoint.replace("{CART_ID}", cartId);

        AddItemToCartRequestModel addItemToCartRequestBody = AddItemToCartRequestModel.builder().productId(productId).quantity(quantity).build();

        Response addItemToCartResponse = RestAssured.given()
                .headers("authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(addItemToCartRequestBody)
                .post(replacedEndPoint);

        return ResponseUtils.deserializeResponse(addItemToCartResponse, AddItemToCartResponseModel.class);

    }
}
