package com.atm.inet.service;


import com.atm.inet.entity.computer.TypePrice;

public interface TypePriceService {
    TypePrice add(TypePrice typePrice);

    public TypePrice findByTypeId(String id);

}
