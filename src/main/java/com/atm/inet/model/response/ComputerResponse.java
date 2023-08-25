package com.atm.inet.model.response;

import com.atm.inet.entity.computer.TypePrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComputerResponse {
    private String id;
    private String name;
    private String code;
    private String category;
    private List<TypePrice> prices;
    private String processor;
    private String ram;
    private String monitor;
    private String ssd;
    private String vga;

}
