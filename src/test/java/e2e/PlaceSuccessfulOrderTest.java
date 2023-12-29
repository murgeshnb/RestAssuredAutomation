package e2e;

import clients.ProductClient;
import clients.UserClient;
import models.auth.SignUpResponseModel;
import models.auth.product.ProductListResponseModel;
import org.testng.annotations.Test;
import utilities.RandomGenerator;

import static org.testng.Assert.assertEquals;

public class PlaceSuccessfulOrderTest extends BaseTest {

    @Test
    public void shouldPlaceOrderSuccessfully() {
        // Arrange
        String email = RandomGenerator.generateRandomEmail();

        // Act
        SignUpResponseModel signUpResponseModel = UserClient.signUp(email, "123456");
        int statusCode = signUpResponseModel.getStatusCode();
        String accessToken = signUpResponseModel.getData().getSession().getAccessToken();

        // Assert
        assertEquals(statusCode, 201, "status code invalid");

        // get list of products
        ProductListResponseModel productListResponseModel = ProductClient.getProductsList(accessToken);
        assertEquals(productListResponseModel.getStatusCode(), 200);

        ProductListResponseModel.ProductModel productModel = productListResponseModel.getProducts().get(0);
        System.out.println(productModel.getName());

        // create cart

        // add item to cart

        // make payment

    }

}
