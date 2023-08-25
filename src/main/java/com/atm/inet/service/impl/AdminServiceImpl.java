package com.atm.inet.service.impl;

import com.atm.inet.entity.Admin;
import com.atm.inet.repository.AdminRepository;
import com.atm.inet.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Admin create(Admin admin) {
        try {
            return adminRepository.save(admin);
        }catch (DataIntegrityViolationException exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email Already Exist");
        }
    }
}
