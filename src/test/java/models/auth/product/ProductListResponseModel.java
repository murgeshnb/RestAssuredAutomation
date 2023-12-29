package models.auth.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductListResponseModel {
    private int statusCode;
    private List<Product> products;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Product {
        @JsonProperty("created_at")
        private String createdAt;
        private String name;
        private String id;
        @JsonProperty("category_id")
        private String categoryId;
        private int price;
    }
}
