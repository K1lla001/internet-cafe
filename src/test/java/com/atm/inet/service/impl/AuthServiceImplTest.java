package com.atm.inet.service.impl;

import com.atm.inet.entity.Admin;
import com.atm.inet.entity.Role;
import com.atm.inet.entity.UserCredential;
import com.atm.inet.entity.constant.ERole;
import com.atm.inet.model.request.AuthRequest;
import com.atm.inet.model.response.RegisterResponse;
import com.atm.inet.repository.UserCredentialRepository;
import com.atm.inet.security.JwtSecurityConfig;
import com.atm.inet.service.AdminService;
import com.atm.inet.service.AuthService;
import com.atm.inet.service.CustomerService;
import com.atm.inet.service.RoleService;
import com.atm.inet.utils.BcryptUtil;
import com.atm.inet.utils.ValidationUtil;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserCredentialRepository userCredentialRepository;
    @Mock
    private AdminService adminService;
    @Mock
    private CustomerService customerService;
    @Mock
    private RoleService roleService;
    @Mock
    private BcryptUtil bcryptUtil;
    @Mock
    private JwtSecurityConfig jwtSecurityConfig;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private ValidationUtil validationUtil;
    @Mock
    private AuthService authService;


    @BeforeEach()
    void setUp() {
        authService = new AuthServiceImpl(userCredentialRepository, bcryptUtil, adminService, customerService, roleService, jwtSecurityConfig, authenticationManager, validationUtil);
    }

    @Test
    void testCreateUserCredential_validData() {

        Role role = new Role();
        role.setRole(ERole.ROLE_ADMIN);

        UserCredential userCredential = new UserCredential();
        userCredential.setEmail("ari@gmail.com");
        userCredential.setPassword("password");
        userCredential.setRole(role);

        when(userCredentialRepository.saveAndFlush(any(UserCredential.class))).thenReturn(userCredential);
        AuthRequest auth = new AuthRequest("ari", "susanto", "ari@gmail.com", "0812345", "rahasia");
        UserCredential result = authService.createUserCredential(auth, role);

        Assertions.assertEquals(userCredential.getEmail(), result.getEmail());
        Assertions.assertEquals(userCredential.getPassword(), result.getPassword());
        Assertions.assertEquals(role, result.getRole());
    }
    @Test
    void testRegisterAdmin_validData() {
        Role role = new Role();
        role.setRole(ERole.ROLE_ADMIN);

        when(userCredentialRepository.saveAndFlush(any(UserCredential.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AuthRequest auth = new AuthRequest("ari", "susanto", "ari@gmail.com", "0812345", "rahasia");

        RegisterResponse registerResponse = authService.registerAdmin(auth);

        Assertions.assertNotNull(registerResponse);
        Assertions.assertEquals(auth.getEmail(), registerResponse.getEmail());

        verify(userCredentialRepository, times(1)).saveAndFlush(any(UserCredential.class));
        verify(adminService, times(1)).create(any(Admin.class));
    }

    @Test
    void testRegisterAdmin_duplicateData() {
        Role role = new Role();
        role.setRole(ERole.ROLE_ADMIN);

        when(userCredentialRepository.saveAndFlush(any(UserCredential.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AuthRequest auth = new AuthRequest("ari", "susanto", "ari@gmail.com", "0812345", "rahasia");

        RegisterResponse registerResponse = authService.registerAdmin(auth);

        Assertions.assertNotNull(registerResponse);
        Assertions.assertEquals(auth.getEmail(), registerResponse.getEmail());

        verify(userCredentialRepository, times(1)).saveAndFlush(any(UserCredential.class));
        verify(adminService, times(1)).create(any(Admin.class));
    }


    @Test
    void registerCustomer() {
    }

    @Test
    void login() {
    }

    @Test
    void createUserCredential() {
    }
}