package com.atm.inet.service;


import com.atm.inet.dto.request.AuthRequest;
import com.atm.inet.dto.response.LoginResponse;
import com.atm.inet.dto.response.RegisterResponse;
import com.atm.inet.entity.Role;
import com.atm.inet.entity.UserCredential;

public interface AuthService {

    RegisterResponse registerAdmin(AuthRequest request);

    LoginResponse login(AuthRequest request);

    UserCredential createUserCredential(AuthRequest request, Role role);


}
