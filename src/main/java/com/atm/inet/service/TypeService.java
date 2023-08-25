package com.atm.inet.service;

import com.atm.inet.entity.computer.Type;
import com.atm.inet.entity.computer.TypePrice;
import com.atm.inet.entity.constant.ECategory;

public interface TypeService {

    Type save(Type type);

    Type getOrSave(ECategory category, TypePrice price);
}
