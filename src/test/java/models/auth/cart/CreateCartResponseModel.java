package models.auth.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.Data;
import utilities.EndPointConfig;
import utilities.ResponseUtils;

@Data
public class CreateCartResponseModel {
    private int statusCode;
    @JsonProperty("cart_id")
    private String cartId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("created_at")
    private String createdAt;
}
