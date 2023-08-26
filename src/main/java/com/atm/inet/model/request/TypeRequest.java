package com.atm.inet.model.request;

import com.atm.inet.entity.constant.ECategory;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeRequest {

    private String id;
    private ECategory category;
    private Long Prices;

}
