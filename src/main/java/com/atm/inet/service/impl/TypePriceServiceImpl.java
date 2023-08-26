package com.atm.inet.service.impl;

import com.atm.inet.entity.computer.TypePrice;
import com.atm.inet.repository.TypePriceRepository;
import com.atm.inet.service.TypePriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TypePriceServiceImpl implements TypePriceService {
    private final TypePriceRepository typePriceRepository;

    @Override
    public TypePrice add(TypePrice typePrice) {
        return typePriceRepository.saveAndFlush(typePrice);
    }
}
