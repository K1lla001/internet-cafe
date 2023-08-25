package com.atm.inet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/me")
    public ResponseEntity<CommonResponse<UserResponse>> getUserInfo(Authentication authentication){
        UserResponse userInfo = userService.getUserInfo(authentication);
        return ResponseEntity.ok(
                CommonResponse.<UserResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully Get User Info")
                        .data(userInfo)
                        .build()
        );
    }

    @PutMapping(path = "/profile-picture")
    public ResponseEntity<CommonResponse<FileResponse>> uploadProfilePicture(@RequestParam(name = "image")MultipartFile multipartFile){
        FileResponse fileResponse = userService.updateProfilePicture(multipartFile);
        return ResponseEntity.ok(
                CommonResponse.<FileResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully Upload Profile Picture!")
                        .data(fileResponse)
                        .build()
        );
    }

}
