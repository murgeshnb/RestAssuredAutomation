package models.auth.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddItemToCartResponseModel {
    private int statusCode;
    @JsonProperty("product_id")
    private String productId;
    private String quantity;
}
