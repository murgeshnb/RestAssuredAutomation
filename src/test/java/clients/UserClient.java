package clients;

import models.auth.SignUpRequestModel;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.auth.SignUpResponseModel;
import utilities.EndPointConfig;
import utilities.ResponseUtils;

public class UserClient {

    public static SignUpResponseModel signUp(String email, String password) {
        String signUpEndPoint = EndPointConfig.getEndPoint("auth", "signUp");
        SignUpRequestModel requestModel = SignUpRequestModel.builder()
                .email(email)
                .password(password)
                .build();

        Response signUpResponse = RestAssured.given().contentType(ContentType.JSON)
                .body(requestModel)
                .post(signUpEndPoint);

        SignUpResponseModel signUpResponseModel = ResponseUtils.deserializeResponse(signUpResponse, SignUpResponseModel.class);
        return signUpResponseModel;
    }
}
