package com.atm.inet.controller;

import com.atm.inet.entity.computer.Computer;
import com.atm.inet.model.common.CommonResponse;
import com.atm.inet.model.request.ComputerRequest;
import com.atm.inet.model.response.ComputerResponse;
import com.atm.inet.service.ComputerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/computers")
public class ComputerController {

    private final ComputerService computerService;

    @PostMapping
    public ResponseEntity<CommonResponse<ComputerResponse>> addComputer(@RequestBody ComputerRequest request){
        ComputerResponse computerResponse = computerService.addComputer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.<ComputerResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully Create New Computer!")
                        .data(computerResponse)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<CommonResponse<?>> getAll(){
        List<Computer> computersData = computerService.getAll();
        return ResponseEntity.ok(CommonResponse.<List<Computer>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully Get All Data")
                        .data(computersData)
                .build());
    }

}
