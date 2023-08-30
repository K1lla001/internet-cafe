package com.atm.inet.controller;

import com.atm.inet.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
class AdminControllerTest {

    private AdminService adminService;
    private AdminController adminController;

    @BeforeEach
    public void setUp() {
        adminService = mock(AdminService.class);
        adminController = new AdminController(adminService);
    }

    @Test
    void getById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}