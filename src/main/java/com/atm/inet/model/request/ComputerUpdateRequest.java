package com.atm.inet.model.request;

import com.atm.inet.model.response.ComputerSpecResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComputerUpdateRequest {

    private String id;
    private String name;
    private String code;
    private ComputerSpecRequest computerSpec;

}
