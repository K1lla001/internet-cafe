package com.atm.inet.service.impl;

import com.atm.inet.entity.computer.Type;
import com.atm.inet.entity.constant.ECategory;
import com.atm.inet.repository.TypeRepository;
import com.atm.inet.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    @Override
    public Type getOrSave(ECategory category) {
        return typeRepository.findByCategory(category).orElseGet(() ->
            typeRepository.saveAndFlush(Type.builder()
                    .category(category)
                    .price(setPrice(category, 0L))
                    .build()));
    }

    @Override
    public Long setPrice(ECategory category, Long updatePrice) {
        long price = 0L;

        if (category.equals(ECategory.EXTREME)) {
            price = 10_000L + updatePrice;
        } else if (category.equals(ECategory.VIP)) {
            price = 8_000L + updatePrice;
        } else if (category.equals(ECategory.REGULAR)) {
            price = 6_000L + updatePrice;
        }

        return price;
    }

}
