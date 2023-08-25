package com.atm.inet.controller;

import com.atm.inet.model.common.CommonResponse;
import com.atm.inet.model.request.ComputerRequest;
import com.atm.inet.model.response.ComputerResponse;
import com.atm.inet.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/computers")
public class ComputerController {

    private final TypeService typeService;

    @PostMapping
    public ResponseEntity<CommonResponse<ComputerResponse>> addComputer(
            @RequestPart(name = "computer")ComputerRequest request,
            @RequestPart(name = "images") List<MultipartFile> multipartFileList
            ){
        ComputerResponse savedComputer = typeService.save(request, multipartFileList);
        return ResponseEntity.ok(
                CommonResponse.<ComputerResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully add data!")
                        .data(savedComputer)
                        .build()
        );
    }

}
