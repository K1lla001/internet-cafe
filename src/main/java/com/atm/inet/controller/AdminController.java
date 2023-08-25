package com.atm.inet.controller;

import com.atm.inet.model.common.CommonResponse;
import com.atm.inet.model.request.UpdateAdminRequest;
import com.atm.inet.model.response.AdminResponse;
import com.atm.inet.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    @PutMapping
    public ResponseEntity<CommonResponse<AdminResponse>> update(@RequestBody UpdateAdminRequest request){
        AdminResponse updatedAdmin = adminService.update(request);
        return ResponseEntity.ok(CommonResponse.<AdminResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully update data!")
                        .data(updatedAdmin)
                .build());
    }

}
