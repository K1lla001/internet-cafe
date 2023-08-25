package com.atm.inet.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class AuthRequest {
    @NotNull(message = "Email Can Not Be Empty")
    private String email;

    @NotNull(message = "Password Can Not Be Empty")
    private String password;
}
