package com.atm.inet.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaypalRequest {
    private Double price;
    private String currency;
    private String methode;
    private String intent;
    private String description;
}
