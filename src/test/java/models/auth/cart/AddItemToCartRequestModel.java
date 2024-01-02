package models.auth.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddItemToCartRequestModel {
    @JsonProperty("product_id")
    private String productId;
    private int quantity;
}
