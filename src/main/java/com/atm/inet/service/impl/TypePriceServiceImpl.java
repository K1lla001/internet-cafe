package com.atm.inet.service.impl;

import com.atm.inet.entity.computer.TypePrice;
import com.atm.inet.repository.TypePriceRepository;
import com.atm.inet.service.TypePriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class TypePriceServiceImpl implements TypePriceService {
    private final TypePriceRepository typePriceRepository;

    @Override
    public TypePrice findByTypeId(String id) {
        return typePriceRepository.findByType_Id(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Price Not Found!"));    }
    @Override
    public TypePrice add(TypePrice typePrice) {
        return typePriceRepository.saveAndFlush(typePrice);
    }
}
