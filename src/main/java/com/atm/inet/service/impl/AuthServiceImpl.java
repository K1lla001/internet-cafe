package com.atm.inet.service.impl;

import com.atm.inet.entity.*;
import com.atm.inet.entity.constant.ERole;
import com.atm.inet.model.request.AuthRequest;
import com.atm.inet.model.response.LoginResponse;
import com.atm.inet.model.response.RegisterResponse;
import com.atm.inet.repository.UserCredentialRepository;
import com.atm.inet.security.JwtSecurityConfig;
import com.atm.inet.service.AdminService;
import com.atm.inet.service.AuthService;
import com.atm.inet.service.CustomerService;
import com.atm.inet.service.RoleService;
import com.atm.inet.utils.BcryptUtil;
import com.atm.inet.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final BcryptUtil bcryptUtil;
    private final AdminService adminService;
    private final RoleService roleService;
    private final JwtSecurityConfig jwtSecurityConfig;
    private final AuthenticationManager authenticationManager;
    private final ValidationUtil validationUtil;
    private final CustomerService customerService;

    @Override
    public RegisterResponse registerAdmin(AuthRequest request) {
        try {
            Role admin = roleService.getOrSave(ERole.ROLE_ADMIN);
            UserCredential userCredential = createUserCredential(request, admin);

            Admin currentAdmin = Admin.builder()
                    .email(request.getEmail())
                    .fullName(defaultNameGenerator(request.getEmail()))
                    .userCredential(userCredential)
                    .build();

            adminService.create(currentAdmin);
            return RegisterResponse.builder().email(currentAdmin.getEmail()).build();
        }catch (DataIntegrityViolationException exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User Already Exist!");
        }
    }

    @Override
    public RegisterResponse registerCustomer(AuthRequest request) {
        validationUtil.validate(request);
        try {
            Role customer = roleService.getOrSave(ERole.ROLE_CUSTOMER);
            UserCredential userCredential = createUserCredential(request, customer);

            Customer currentCustomer = Customer.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .isMember(false)
                    .userCredential(userCredential)
                    .build();

            customerService.addCustomer(currentCustomer);
            return RegisterResponse.builder()
                    .email(currentCustomer.getEmail()).build();
        }catch (DataIntegrityViolationException exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User Already Exist!");
        }
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserDetailsImpl user = (UserDetailsImpl) authenticate.getPrincipal();
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        String currentRole = "";
        if(!roles.isEmpty()) currentRole = roles.get(0);

        String jwtToken = jwtSecurityConfig.generateToken(user.getEmail());
        return LoginResponse.builder()
                .email(user.getEmail())
                .role(currentRole)
                .token(jwtToken)
                .build();
    }

    @Override
    public UserCredential createUserCredential(AuthRequest request, Role role) {
        return userCredentialRepository.saveAndFlush(
                UserCredential.builder()
                        .email(request.getEmail())
                        .password(bcryptUtil.hashPassword(request.getPassword()))
                        .role(role)
                        .build()
        );
    }

    private String defaultNameGenerator(String fullName){
        int currentAt = fullName.indexOf("@");
        if(currentAt != -1){
            return fullName.substring(0, currentAt);
        }
        return fullName;
    }
}
