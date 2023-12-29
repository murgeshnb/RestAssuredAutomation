package clients;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.auth.product.ProductListResponseModel;
import utilities.EndPointConfig;
import utilities.ResponseUtils;

public class ProductClient {
    public static ProductListResponseModel getProductsList(String accessToken){
        String productListEndPoint = EndPointConfig.getEndPoint("product", "getProductsList");

        Response response = RestAssured.given()
                .headers("authorization", "Bearer " + accessToken)
                .get(productListEndPoint);

        ProductListResponseModel productListResponseModel = ResponseUtils.deserializeResponse(response, ProductListResponseModel.class);
        return productListResponseModel;
    }
}
