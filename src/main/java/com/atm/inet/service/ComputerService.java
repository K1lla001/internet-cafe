package com.atm.inet.service;

import com.atm.inet.entity.computer.Computer;
import com.atm.inet.model.request.ComputerRequest;
import com.atm.inet.model.response.ComputerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComputerService {

    ComputerResponse addComputer(ComputerRequest newComputer);
    List<Computer> getAll();

    Page<Computer> getComputerPerPage (Pageable pageable);
    ComputerResponse getComputerById(String id);
    ComputerResponse updateComputer(ComputerRequest updateComputer);
}
