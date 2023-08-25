package com.atm.inet.service;


import com.atm.inet.entity.computer.Type;
import com.atm.inet.entity.constant.ECategory;

public interface TypeService {

    Type getOrSave(ECategory category);

    Long setPrice(ECategory category, Long updatePrice);

}
