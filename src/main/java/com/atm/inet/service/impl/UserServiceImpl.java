package com.atm.inet.service.impl;

import com.atm.inet.entity.ProfilePicture;
import com.atm.inet.entity.UserCredential;
import com.atm.inet.entity.UserDetailsImpl;
import com.atm.inet.model.response.FileResponse;
import com.atm.inet.model.response.UserResponse;
import com.atm.inet.repository.UserCredentialRepository;
import com.atm.inet.service.ProfilePictureService;
import com.atm.inet.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserCredentialRepository userCredentialRepository;
    private final ProfilePictureService profilePictureService;


    @Override
    public UserResponse getUserInfo(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        UserCredential userCredential = userCredentialRepository.findByEmail(principal.getEmail()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Credentials"));

            log.warn("PROFILE PICTURE : {}", userCredential.getProfilePicture());
            return UserResponse.builder()
                    .userId(userCredential.getId())
                    .email(userCredential.getEmail())
                    .role(userCredential.getRole().getRole().name())
                    .fileResponse(Objects.nonNull(userCredential.getProfilePicture()) ?
                            FileResponse.builder()
                                    .id(userCredential.getProfilePicture().getId())
                                    .filename(userCredential.getProfilePicture().getName())
                                    .url("/api/users/profile-picture/" + userCredential.getProfilePicture().getId())
                                    .build(): null)
                    .build();
    }

    @Override
    public UserCredential getByAuthentication(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userCredentialRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials!"));

    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public FileResponse updateProfilePicture(MultipartFile multipartFile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserCredential userCredential = getByAuthentication(authentication);
        ProfilePicture profilePicture = profilePictureService.create(userCredential, multipartFile);
        return FileResponse.builder()
                .id(profilePicture.getId())
                .filename(profilePicture.getName())
                .url("/api/v1/users/profile-picture/" + profilePicture.getId())
                .build();

    }
    @Override
    public void deleteProfilePicture(String imageId) {
        profilePictureService.deleteById(imageId);

    }

}
