package com.atm.inet.service;

import com.atm.inet.model.request.ComputerRequest;
import com.atm.inet.model.response.ComputerResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TypeService {
    ComputerResponse save(ComputerRequest request, List<MultipartFile> computerImage);

}
