package com.atm.inet.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder()
public class CommonResponse<T> {
    private Integer statusCode;
    private String message;
    private T data;
}

