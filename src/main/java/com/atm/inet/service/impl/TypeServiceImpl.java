package com.atm.inet.service.impl;

import com.atm.inet.entity.computer.*;
import com.atm.inet.entity.constant.ECategory;
import com.atm.inet.repository.TypeRepository;
import com.atm.inet.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    @Override
    public Type save(Type type) {
        return typeRepository.saveAndFlush(type);
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
}
