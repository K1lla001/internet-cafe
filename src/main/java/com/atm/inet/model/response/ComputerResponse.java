package com.atm.inet.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComputerResponse {
    private String id;
    private String name;
    private String code;
    private String category;
    private Long price;
    private String processor;
    private String ram;
    private String monitor;
    private String ssd;
    private String vga;

}
