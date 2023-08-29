package com.atm.inet.service.impl;

import com.atm.inet.entity.computer.*;
import com.atm.inet.entity.constant.ECategory;
import com.atm.inet.model.request.TypeRequest;
import com.atm.inet.model.response.FileResponse;
import com.atm.inet.model.response.TypePriceResponse;
import com.atm.inet.model.response.TypeResponse;
import com.atm.inet.repository.TypeRepository;
import com.atm.inet.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;
    private final ComputerImageService computerImageService;

    @Override
    public Type findTypeById(String id) {
        return typeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Type Not Found!"));
    }

    @Override
    public TypeResponse findById(String id){
        Type type = typeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Type Not Found!"));

        return typeResponseGenerator(type);
    }

    private TypeResponse typeResponseGenerator(Type type) {
        List<TypePriceResponse> priceResponses = type.getTypePrices().stream().map(typePrice -> TypePriceResponse.builder()
                .id(typePrice.getId())
                .price(typePrice.getPrice())
                .isActive(typePrice.getIsActive())
                .build()).toList();

        FileResponse image = FileResponse.builder()
                .id(type.getComputerImage().getId())
                .filename(type.getComputerImage().getName())
                .url("/api/v1/types/image/" + type.getComputerImage().getId())
                .build();

        return TypeResponse.builder()
                .id(type.getId())
                .category(type.getCategory().name())
                .prices(priceResponses)
                .image(image)
                .build();
    }

    @Override
    public TypeResponse update(TypeRequest request) {
        return null;
    }

    @Override
    public Type getOrSave(ECategory category, TypePrice price) {
        return typeRepository.findByCategory(category).orElseGet(() ->
                typeRepository.saveAndFlush(Type.builder()
                        .category(category)
                        .typePrices(List.of(price))
                        .build())
        );
    }

    @Override
    public List<TypeResponse> getAll() {
        List<Type> types = typeRepository.findAll();

        List<TypeResponse> typeResponses = new ArrayList<>();
       types.forEach(type -> typeResponses.add(typeResponseGenerator(type)));

       return typeResponses;
    }

    @Override
    public Resource downloadComputerImg(String id) {
        return computerImageService.downloadImage(id);
    }

}
