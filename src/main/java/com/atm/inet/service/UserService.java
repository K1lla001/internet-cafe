package com.atm.inet.service;


import com.enigma.ICafe.entity.UserCredential;
import com.enigma.ICafe.model.response.FileResponse;
import com.enigma.ICafe.model.response.UserResponse;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserResponse getUserInfo(Authentication authentication);
    UserCredential getByAuthentication(Authentication authentication);
    Resource downloadProfilePicture(String imageId);

    FileResponse updateProfilePicture(MultipartFile multipartFile);
    String deleteProfilePicture(String imageId);
}
