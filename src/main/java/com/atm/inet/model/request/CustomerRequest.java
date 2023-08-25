package com.atm.inet.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerRequest {

    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Boolean isMember;
    private Boolean isDeleted;

}
