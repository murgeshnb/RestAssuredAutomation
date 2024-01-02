package clients;

import io.restassured.response.Response;
import models.auth.product.ProductListResponseModel;
import utilities.CommonRequestSpec;
import utilities.EndPointConfig;
import utilities.ResponseUtils;

import static io.restassured.RestAssured.given;

public class ProductClient {
    public static ProductListResponseModel getProductsList(String accessToken){
        String productListEndPoint = EndPointConfig.getEndPoint("product", "getProductsList");

        Response productListResponse = given()
                .spec(CommonRequestSpec.authRequestSpecBuilder(accessToken))
                .get(productListEndPoint);

        ProductListResponseModel productListResponseModel = ResponseUtils.deserializeResponse(productListResponse, ProductListResponseModel.class);
        return productListResponseModel;
    }
}
