package e2e;

import clients.CartClient;
import clients.PaymentClient;
import clients.ProductClient;
import clients.UserClient;
import models.auth.SignUpResponseModel;
import models.auth.cart.AddItemToCartResponseModel;
import models.auth.cart.CreateCartResponseModel;
import models.auth.payment.MakePaymentResponseModel;
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

        ProductListResponseModel.Product product = productListResponseModel.getProducts().get(0);
        String productId = product.getId();
        int productPrice = product.getPrice();
        System.out.println(productPrice);
        System.out.println(product.getName());

        // create cart
        CreateCartResponseModel cartResponseModel = CartClient.createCart(accessToken);
        assertEquals(cartResponseModel.getStatusCode(), 201, "crate cart failed");
        String cartId = cartResponseModel.getCartId();
        System.out.println(cartId);

        // add item to cart
        int quantity = 4;
        AddItemToCartResponseModel addItemToCartResponseModel = CartClient.addItemToCart(accessToken, cartId, productId, quantity);
        assertEquals(addItemToCartResponseModel.getStatusCode(), 201, "add item to cart failed");

        // make payment
        MakePaymentResponseModel makePaymentResponseModel = PaymentClient.completePayment(accessToken);
        System.out.println(makePaymentResponseModel.getMessage());
        assertEquals(makePaymentResponseModel.getStatusCode(), 200, "payment failed");
        assertEquals(makePaymentResponseModel.getMessage(), "payment success", "payment failure");
        System.out.println("total paid "+ makePaymentResponseModel.getAmountPaid());
        int x = quantity * productPrice;
        System.out.println("actual "+x);

    }

}
