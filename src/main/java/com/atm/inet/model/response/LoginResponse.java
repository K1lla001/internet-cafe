package com.atm.inet.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    String email;
    String role;
    String token;

}
