package com.atm.inet.controller;

import com.atm.inet.entity.constant.ECategory;
import com.atm.inet.model.common.CommonResponse;
import com.atm.inet.model.common.ComputerSearch;
import com.atm.inet.model.common.PagingResponse;
import com.atm.inet.model.request.ComputerRequest;
import com.atm.inet.model.response.ComputerResponse;
import com.atm.inet.model.response.NewComputerResponse;
import com.atm.inet.service.ComputerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(name = "size", defaultValue = "5", required = false) Integer size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String processor,
            @RequestParam(required = false) String vga,
            @RequestParam(required = false) String category
            ){
        ComputerSearch dataSearch = ComputerSearch.builder()
                .name(name)
                .code(code)
                .processor(processor)
                .vga(vga)
                .category(category)
                .build();

        PageRequest pageRequest = PageRequest.of(pageNumber, size);

        Page<ComputerResponse> pageableResponse = computerService.getAll(pageRequest, dataSearch);

        PagingResponse pagingResponse = PagingResponse.builder()
                .count(pageableResponse.getTotalElements())
                .totalPages(pageableResponse.getTotalPages())
                .page(pageNumber)
                .size(size)
                .build();

        return ResponseEntity.ok(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully Get Data Per Page!")
                        .data(pageableResponse.getContent())
                        .pagingResponse(pagingResponse)
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
