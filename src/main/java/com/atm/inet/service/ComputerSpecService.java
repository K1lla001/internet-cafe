package com.atm.inet.service;


import com.atm.inet.entity.computer.ComputerSpec;

public interface ComputerSpecService {

    ComputerSpec addSpec(ComputerSpec newSpec);

    ComputerSpec updateSpec(ComputerSpec updateSpec);

}
