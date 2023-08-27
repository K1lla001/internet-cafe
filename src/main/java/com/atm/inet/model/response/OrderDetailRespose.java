package com.atm.inet.model.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor@AllArgsConstructor
@Builder
public class OrderDetailRespose {

    private String orderId;
    private String computerCode;
    private String computerName;
    private String type;
    private Long price;
    private String customerFirstName;
    private String customerLastName;
    private String customerPhoneNumber;
    private String customerEmail;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endBookingDate;

}
