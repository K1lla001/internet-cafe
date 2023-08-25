package com.atm.inet.service.impl;

import com.atm.inet.entity.computer.Computer;
import com.atm.inet.entity.computer.ComputerSpec;
import com.atm.inet.entity.computer.Type;
import com.atm.inet.entity.constant.ECategory;
import com.atm.inet.model.request.ComputerRequest;
import com.atm.inet.model.response.ComputerResponse;
import com.atm.inet.repository.ComputerRepository;
import com.atm.inet.service.ComputerService;
import com.atm.inet.service.ComputerSpecService;
import com.atm.inet.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class ComputerServiceImpl implements ComputerService {

    private final ComputerRepository computerRepository;
    private final TypeService typeService;
    private final ComputerSpecService specService;

    @Override
    public ComputerResponse addComputer(ComputerRequest newComputer) {
        Type computerType = typeService.getOrSave(ECategory.valueOf(newComputer.getCategory()));

        ComputerSpec computerSpec = ComputerSpec.builder()
                .processor(newComputer.getProcessor())
                .ram(newComputer.getRam())
                .monitor(newComputer.getMonitor())
                .ssd(newComputer.getSsd())
                .vga(newComputer.getVga())
                .build();

        specService.addSpec(computerSpec);

        Computer completeComputer = Computer.builder()
                .name(newComputer.getName())
                .code(newComputer.getCode())
                .type(computerType)
                .status(true)
                .specification(computerSpec)
                .build();

        Computer createdComputer = computerRepository.save(completeComputer);

        return responseGenerator(createdComputer);
    }

    @Override
    public List<Computer> getAll() {
        return computerRepository.findAll();
    }

    @Override
    public Page<Computer> getComputerPerPage(Pageable pageable) {
        //TODO: Get Todo Perpage
        return null;
    }

    @Override
    public ComputerResponse getComputerById(String id) {
        return null;
    }

    @Override
    public ComputerResponse updateComputer(ComputerRequest updateComputer) {
        return null;
    }

    private ComputerResponse responseGenerator(Computer computer) {
        return ComputerResponse.builder()
                .id(computer.getId())
                .name(computer.getName())
                .code(computer.getCode())
                .category(String.valueOf(computer.getType().getCategory()))
                .price(computer.getType().getPrice())
                .processor(computer.getSpecification().getProcessor())
                .ram(computer.getSpecification().getRam())
                .monitor(computer.getSpecification().getMonitor())
                .ssd(computer.getSpecification().getSsd())
                .vga(computer.getSpecification().getVga())
                .build();
    }
}
