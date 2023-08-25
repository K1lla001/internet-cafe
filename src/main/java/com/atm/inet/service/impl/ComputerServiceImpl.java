package com.atm.inet.service.impl;

import com.atm.inet.entity.computer.*;
import com.atm.inet.entity.constant.ECategory;
import com.atm.inet.model.request.ComputerRequest;
import com.atm.inet.model.response.*;
import com.atm.inet.repository.ComputerRepository;
import com.atm.inet.service.ComputerImageService;
import com.atm.inet.service.ComputerService;
import com.atm.inet.service.ComputerSpecService;
import com.atm.inet.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComputerServiceImpl implements ComputerService {

    private final ComputerRepository computerRepository;
    private final ComputerImageService computerImageService;
    private final ComputerSpecService computerSpecService;
    private final TypeService typeService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public NewComputerResponse save(ComputerRequest request, List<MultipartFile> multipartFiles) {

        TypePrice typePrice = TypePrice.builder()
                .price(request.getPrice())
                .isActive(true)
                .build();

        Type type = typeService.getOrSave(ECategory.valueOf(request.getCategory()), typePrice);

        typePrice.setType(type);

        List<ComputerImage> computerImages = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            ComputerImage computerImage = computerImageService.create(type, multipartFile);
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

        computerRepository.saveAndFlush(computer);

        return generateNewComputerResponse(computer, type, computerSpec);
    }

    @Override
    public List<ComputerResponse> getAll() {
        List<Computer> computers = computerRepository.findAll();
        List<ComputerResponse> computerResponses = new ArrayList<>();

        computers.forEach(computer ->
                computerResponses.add(generateComputerResponse(computer))
        );
        return computerResponses;
    }

    private Computer getOriginalComputerById(String id) {
        return computerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Computer not found!"));
    }

    private ComputerResponse generateComputerResponse(Computer computer) {
        List<TypePriceResponse> priceResponses = computer.getType().getTypePrices().stream()
                .map(typePrice -> TypePriceResponse.builder()
                        .id(typePrice.getId())
                        .price(typePrice.getPrice())
                        .isActive(typePrice.getIsActive())
                        .build())
                .toList();

        TypeResponse typeResponse = TypeResponse.builder()
                .id(computer.getType().getId())
                .category(computer.getType().getCategory().name())
                .typePriceResponses(priceResponses)
                .imageList(generateFileResponse(computer.getType()))
                .build();

        return ComputerResponse.builder()
                .id(computer.getId())
                .name(computer.getName())
                .code(computer.getCode())
                .type(typeResponse)
                .computerSpecResponse(computer.getSpecification())
                .build();
    }

    private List<ComputerImageResponse> generateFileResponse(Type type) {
        return type.getComputerImages().stream().map(computerImage ->
                ComputerImageResponse.builder()
                        .id(computerImage.getId())
                        .name(computerImage.getName())
                        .contentType(computerImage.getContentType())
                        .path(computerImage.getPath())
                        .build()).toList();
    }

    private NewComputerResponse generateNewComputerResponse(Computer computer, Type type, ComputerSpec computerSpec) {

        List<TypePriceResponse> priceResponses = new ArrayList<>();

        type.getTypePrices().forEach(typePrice ->
                priceResponses.add(TypePriceResponse.builder()
                        .id(typePrice.getId())
                        .price(typePrice.getPrice())
                        .isActive(typePrice.getIsActive())
                        .build())
        );

        return NewComputerResponse.builder()
                .id(computer.getId())
                .name(computer.getName())
                .code(computer.getCode())
                .category(type.getCategory().name())
                .price(priceResponses)
                .processor(computerSpec.getProcessor())
                .ram(computerSpec.getRam())
                .monitor(computerSpec.getMonitor())
                .ssd(computerSpec.getSsd())
                .vga(computerSpec.getVga())
                .build();
    }
}
