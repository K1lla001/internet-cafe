package com.atm.inet.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComputerImageResponse {

    private String id;
    private String name;
    private String path;
    private String contentType;

}