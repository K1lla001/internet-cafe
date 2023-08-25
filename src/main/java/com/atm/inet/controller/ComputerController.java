package com.atm.inet.controller;

import com.atm.inet.model.common.CommonResponse;
import com.atm.inet.model.request.ComputerRequest;
import com.atm.inet.model.response.ComputerResponse;
import com.atm.inet.model.response.NewComputerResponse;
import com.atm.inet.service.ComputerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/computers")
public class ComputerController {

    private final ComputerService computerService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse<NewComputerResponse>> addComputer(
            @RequestPart(name = "computer")ComputerRequest request,
            @RequestPart(name = "images") List<MultipartFile> multipartFileList
            ){
        NewComputerResponse savedComputer = computerService.save(request, multipartFileList);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.<NewComputerResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully add data!")
                        .data(savedComputer)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ComputerResponse>>> getAll(){
        List<ComputerResponse> computerResponseList = computerService.getAll();

        return ResponseEntity.ok(
                CommonResponse.<List<ComputerResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get all data computers")
                        .data(computerResponseList)
                        .build()
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<ComputerResponse>> getById(@PathVariable String id){
        ComputerResponse response = computerService.getById(id);
        return ResponseEntity.ok(
                CommonResponse.<ComputerResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get data!")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<String>> deleteById(@PathVariable String id){
        String deletedEntity = computerService.deleteById(id);
        return ResponseEntity.ok(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully delete data!")
                        .data(deletedEntity)
                        .build()
        );
    }

}
