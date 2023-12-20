package requestModels;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SignupRequestModel {
    private String email;
    private String password;
}
