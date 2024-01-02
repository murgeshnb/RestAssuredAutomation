package models.auth;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SignUpRequestModel {
//    public static class SignupRequestModel {
        private String email;
        private String password;
//    }
}
