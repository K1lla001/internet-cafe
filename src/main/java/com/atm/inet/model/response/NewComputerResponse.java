package com.atm.inet.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewComputerResponse {

    String id;
    String name;
    String code;
    String category;
    List<TypePriceResponse> price;
    String processor;
    String ram;
    String monitor;
    String ssd;
    String vga;

}
