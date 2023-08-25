package com.atm.inet.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {

    private String id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Boolean isMember;
    private Boolean isDeleted;
    private String role;

}
