package clients;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.auth.payment.MakePaymentResponseModel;
import utilities.CommonRequestSpec;
import utilities.EndPointConfig;
import utilities.ResponseUtils;

import static io.restassured.RestAssured.given;

public class PaymentClient {

    public static MakePaymentResponseModel completePayment(String accessToken) {
        String paymentEndPoint = EndPointConfig.getEndPoint("payment", "makePayment");

        Response paymentResponse = given()
                .spec(CommonRequestSpec.authRequestSpecBuilder(accessToken))
                .post(paymentEndPoint);

        return ResponseUtils.deserializeResponse(paymentResponse, MakePaymentResponseModel.class);
    }
}
