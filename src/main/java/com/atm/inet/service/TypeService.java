package com.atm.inet.service;

import com.atm.inet.entity.computer.Type;
import com.atm.inet.entity.computer.TypePrice;
import com.atm.inet.entity.constant.ECategory;
import com.atm.inet.model.request.TypeRequest;
import com.atm.inet.model.response.TypeResponse;

public interface TypeService {

    Type save(Type type);

    Type getOrSave(ECategory category, TypePrice price);
    TypeResponse update(TypeRequest request);

    TypeResponse getByCategory(ECategory category);
}
