package com.atm.inet.service;

import com.atm.inet.entity.Admin;
import com.atm.inet.model.request.UpdateAdminRequest;
import com.atm.inet.model.response.AdminResponse;
import org.springframework.security.core.Authentication;

public interface AdminService {

    AdminResponse authenticateUser(Authentication authentication);
    Admin create(Admin admin);

    AdminResponse update(UpdateAdminRequest request);

    Admin findById(String id);
}
