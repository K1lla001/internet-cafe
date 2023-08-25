package com.atm.inet.service.impl;

import com.atm.inet.entity.computer.ComputerSpec;
import com.atm.inet.repository.ComputerSpecRepository;
import com.atm.inet.service.ComputerSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComputerSpecServiceImpl implements ComputerSpecService {

    private final ComputerSpecRepository specRepository;

    @Override
    public ComputerSpec addSpec(ComputerSpec newSpec) {
        return specRepository.save(newSpec);
    }

    @Override
    public ComputerSpec updateSpec(ComputerSpec updateSpec) {
        return null;
    }
}
