package com.atm.inet.service.impl;

import com.atm.inet.entity.computer.Computer;
import com.atm.inet.repository.ComputerRepository;
import com.atm.inet.service.ComputerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComputerServiceImpl implements ComputerService {

    private final ComputerRepository computerRepository;

    @Override
    public Computer add(Computer request) {
        return computerRepository.saveAndFlush(request);
    }
}
