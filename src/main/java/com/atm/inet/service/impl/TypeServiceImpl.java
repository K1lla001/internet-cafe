package com.atm.inet.service.impl;

import com.atm.inet.entity.computer.*;
import com.atm.inet.entity.constant.ECategory;
import com.atm.inet.model.request.TypeRequest;
import com.atm.inet.model.response.TypePriceResponse;
import com.atm.inet.model.response.TypeResponse;
import com.atm.inet.repository.TypeRepository;
import com.atm.inet.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;
    private final TypePriceService typePriceService;

    @Override
    public Type findById(String id){
        return typeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Type Not Found!"));
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
    public TypeResponse update(TypeRequest request) {
        Type data = typeRepository.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Type Not Found"));

        TypePrice price = data.getTypePrices().stream().filter(TypePrice::getIsActive).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Price Not Found"));

        if (!Objects.equals(request.getPrices(), price.getPrice())){
            price.setIsActive(false);
            typePriceService.add(TypePrice.builder()
                    .type(data)
                    .price(request.getPrices())
                    .build());
        }
        return this.toResponse(data);
    }

    @Override
    public TypeResponse getByCategoryResponse(ECategory category) {
        return this.toResponse(Objects.requireNonNull(typeRepository.findByCategory(category).orElse(null)));
    }

    @Override
    public Type getByCategory(ECategory category) {
        return Objects.requireNonNull(typeRepository.findByCategory(category).orElse(null));
    }

    private TypeResponse toResponse(Type type){
        TypePrice price = type.getTypePrices().stream().filter(TypePrice::getIsActive).findFirst().orElse(null);
        TypePriceResponse priceResponse = TypePriceResponse.builder()
                .id(Objects.requireNonNull(price).getId())
                .price(price.getPrice())
                .build();

        return TypeResponse.builder()
                .id(type.getId())
                .category(type.getCategory().name())
                .prices(priceResponse)
                .build();
    }


}
