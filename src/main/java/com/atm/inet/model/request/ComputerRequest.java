package com.atm.inet.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NotBlank
@NoArgsConstructor
@AllArgsConstructor
public class ComputerRequest {
    private String name;
    private String code;
    private String category;
    private String processor;
    private String ram;
    private String monitor;
    private String ssd;
    private String vga;
}
