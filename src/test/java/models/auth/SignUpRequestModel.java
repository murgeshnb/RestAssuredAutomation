package models.auth;

import lombok.Builder;
import lombok.Data;

public class SignUpRequestModel {
    @Builder
    @Data
    public static class SignupRequestModel {
        private String email;
        private String password;
    }
}
