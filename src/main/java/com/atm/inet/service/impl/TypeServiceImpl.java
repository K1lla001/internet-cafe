package com.atm.inet.service.impl;

import com.atm.inet.entity.computer.*;
import com.atm.inet.entity.constant.ECategory;
import com.atm.inet.model.request.ComputerRequest;
import com.atm.inet.model.response.ComputerResponse;
import com.atm.inet.repository.TypeRepository;
import com.atm.inet.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;
    private final ComputerService computerService;
    private final ComputerImageService computerImageService;
    private final ComputerSpecService computerSpecService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    @PrePersist
    public ComputerResponse save(ComputerRequest request, List<MultipartFile> multipartFiles) {
        TypePrice typePrice = TypePrice.builder()
                .price(request.getPrice())
                .isActive(true)
                .build();

        Type type = Type.builder()
                .category(ECategory.valueOf(request.getCategory()))
                .typePrices(List.of(typePrice))
                .build();

        typeRepository.saveAndFlush(type);

        typePrice.setType(type);

        List<ComputerImage> computerImages = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            ComputerImage computerImage = computerImageService.create(type,multipartFile);
            computerImages.add(computerImage);
        }

        type.setComputerImages(computerImages);

        ComputerSpec computerSpec = ComputerSpec.builder()
                .processor(request.getProcessor())
                .ram(request.getRam())
                .monitor(request.getMonitor())
                .ssd(request.getSsd())
                .vga(request.getVga())
                .build();

        computerSpecService.add(computerSpec);

        Computer computer = Computer.builder()
                .name(request.getName())
                .code(request.getCode())
                .status(true)
                .specification(computerSpec)
                .type(type)
                .build();


        computerService.add(computer);

        return generateResponse(computer, type, computerSpec);
    }


    private ComputerResponse generateResponse(Computer computer, Type type, ComputerSpec computerSpec) {
        return ComputerResponse.builder()
                .id(computer.getId())
                .name(computer.getName())
                .code(computer.getCode())
                .category(type.getCategory().name())
                .prices(type.getTypePrices())
                .processor(computerSpec.getProcessor())
                .ram(computerSpec.getRam())
                .monitor(computerSpec.getMonitor())
                .ssd(computerSpec.getSsd())
                .vga(computerSpec.getVga())
                .build();
    }
}
