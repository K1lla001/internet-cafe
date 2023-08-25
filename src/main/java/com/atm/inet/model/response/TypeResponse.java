package com.atm.inet.model.response;


import com.atm.inet.entity.computer.TypePrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeResponse {
    private String id;
    private String category;
    private List<TypePriceResponse> typePriceResponses;
    private List<ComputerImageResponse> imageList;

}
