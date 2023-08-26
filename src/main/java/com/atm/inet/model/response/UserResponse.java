package com.atm.inet.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private String userId;
    private @Nullable String firstName;
    private @Nullable String lastName;
    private @Nullable String fullName;
    private String email;
    private String phoneNumber;
    private @Nullable Boolean isMember;
    private String role;
    private FileResponse fileResponse;


}
