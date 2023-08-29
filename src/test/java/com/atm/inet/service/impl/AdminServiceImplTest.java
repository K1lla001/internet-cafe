package com.atm.inet.service.impl;


import com.atm.inet.entity.Admin;
import com.atm.inet.entity.UserDetailsImpl;
import com.atm.inet.entity.constant.ERole;
import com.atm.inet.model.response.AdminResponse;
import com.atm.inet.repository.AdminRepository;
import com.atm.inet.repository.UserCredentialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private UserCredentialRepository userCredentialRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AdminServiceImpl adminService;


    //ENTITY
    Admin admin;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.admin = new Admin("1", "ari@gmail.com", "arisu", "0813980021", null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("Test success authenticate user")
    public void testAuthenticateUser_validValue() {
        UserDetailsImpl userDetails = new UserDetailsImpl("1", "ari@gmail.com", "rahasia", List.of(new SimpleGrantedAuthority(ERole.ROLE_ADMIN.name())));
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(adminRepository.findFirstByUserCredential_Email(userDetails.getEmail())).thenReturn(Optional.of(this.admin));

        AdminResponse response = adminService.authenticateUser(authentication);

        assertNotNull(response);
        assertEquals(this.admin.getEmail(), response.getEmail());
        assertEquals(userDetails.getEmail(), response.getEmail());
    }

    @Test
    @DisplayName("Test failed authenticate user")
    public void testAuthenticateUser_invalidValue() {
        UserDetailsImpl userDetails = new UserDetailsImpl("1", "ari@gmail.com", "rahasia", List.of(new SimpleGrantedAuthority(ERole.ROLE_ADMIN.name())));
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(adminRepository.findFirstByUserCredential_Email(userDetails.getEmail()))
                .thenThrow(ResponseStatusException.class);

        assertThrows(ResponseStatusException.class, () -> {
            adminService.authenticateUser(authentication);
        });

    }

    @Test
    @DisplayName("Test Success Create Admin")
    public void testCreateAdmin_validValue() {

        when(adminRepository.save(admin)).thenReturn(admin);

        Admin createdAdmin = adminService.create(admin);

        assertNotNull(createdAdmin);
        assertEquals(createdAdmin.getEmail(), this.admin.getEmail());
    }

    @Test
    @DisplayName("test Failed Create Admin")
    public void testCreateAdmin_invalidValue(){

        when(adminRepository.save(admin)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> {
           adminService.create(admin);
        });
    }

    @Test
    @DisplayName("test success find by id Admin")
    public void testFindById_validAdminId(){

        when(adminRepository.findById("1")).thenReturn(Optional.ofNullable(admin));

        Admin foundedAdmin = adminService.findById("1");

        assertNotNull(foundedAdmin);
        assertEquals(admin.getId(), foundedAdmin.getId());

    }

    @Test
    @DisplayName("test failed find by id admin")
    public void testFindById_invalidAdminId(){

        when(adminRepository.findById("2")).thenThrow(ResponseStatusException.class);

        assertThrows(ResponseStatusException.class, () -> {
            Admin foundedAdmin = adminService.findById("2");
            assertNotEquals(admin.getId(), foundedAdmin.getId());
            assertNull(foundedAdmin);

        });

    }

    @Test
    @DisplayName("Test Success find by email admin")
    public void testFindByEmail_validEmail() {
        when(adminRepository.findFirstByUserCredential_Email("ari@gmail.com")).thenReturn(Optional.ofNullable(admin));

        Admin adminByEmail = adminService.findByEmail("ari@gmail.com");

        assertNotNull(adminByEmail);
        assertEquals(adminByEmail.getEmail(), admin.getEmail());
    }

    @Test
    @DisplayName("Test failed find by email admin")
    public void testFindByEmail_invalidEmail() {
        when(adminRepository.findFirstByUserCredential_Email("ari@yahoo.com")).thenThrow(ResponseStatusException.class);

        assertThrows(ResponseStatusException.class, () -> {
           adminService.findByEmail("ari@yahoo.com");
        });
    }

//    @Test
//    @DisplayName("Test success delete admin by id")
//    public void shouldDeleteAdminByIdValidAdminId() {
//        // Arrange
//        Admin createdAdmin = new Admin("1", "arisu@gmail.com", "arisusanto", "081398213", null);
//        when(adminRepository.findById("1")).thenReturn(Optional.of(createdAdmin));
//        doNothing().when(adminRepository).delete(createdAdmin);
//
//        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
//        AdminResponse response = adminService.authenticateUser(authentication1);
//        // Act
//        adminService.delete(response.getAdminId());
//
//        // Assert
//        verify(adminRepository, times(1)).delete(createdAdmin);
//    }



}
