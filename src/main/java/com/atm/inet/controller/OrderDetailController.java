package com.atm.inet.controller;


import com.atm.inet.entity.OrderDetail;
import com.atm.inet.model.common.CommonResponse;
import com.atm.inet.model.request.OrderDetailRequest;
import com.atm.inet.model.response.OrderDetailRespose;
import com.atm.inet.model.response.PaymentResponse;
import com.atm.inet.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/orders")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @PostMapping
    public ResponseEntity<CommonResponse<PaymentResponse>> order(@RequestBody OrderDetailRequest request){
        PaymentResponse paymentResponse = orderDetailService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        CommonResponse.<PaymentResponse>builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .message("Successfully Create Order!")
                                .data(paymentResponse)
                                .build()
                );
    }

}
