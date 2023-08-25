package com.atm.inet.service;

import com.atm.inet.model.request.ComputerRequest;
import com.atm.inet.model.response.ComputerResponse;
import com.atm.inet.model.response.NewComputerResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ComputerService {

    NewComputerResponse save(ComputerRequest request, List<MultipartFile> computerImage);

    List<ComputerResponse> getAll();

    ComputerResponse getById(String id);

    String deleteById(String id);



}
