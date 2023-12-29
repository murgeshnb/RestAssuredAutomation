package clients;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.auth.payment.MakePaymentResponseModel;
import utilities.EndPointConfig;
import utilities.ResponseUtils;

public class PaymentClient {

    public static MakePaymentResponseModel completePayment(String accessToken) {
        String paymentEndPoint = EndPointConfig.getEndPoint("payment", "makePayment");

        Response paymentResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .headers("authorization", "Bearer " + accessToken)
                .post(paymentEndPoint);
        System.out.println(paymentResponse.statusCode());

        return ResponseUtils.deserializeResponse(paymentResponse, MakePaymentResponseModel.class);
    }
}
