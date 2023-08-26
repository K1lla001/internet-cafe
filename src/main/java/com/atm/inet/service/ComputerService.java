package com.atm.inet.service;

import com.atm.inet.entity.computer.Computer;
import com.atm.inet.model.common.ComputerSearch;
import com.atm.inet.model.request.ComputerRequest;
import com.atm.inet.model.response.ComputerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ComputerService {

    ComputerResponse save(ComputerRequest request, List<MultipartFile> computerImage);

    Page<ComputerResponse> getAll(Pageable pageable, ComputerSearch computerSearch);
    ComputerResponse updateComputer(ComputerRequest updateComputer);

    Computer findByTypeId(String id);

    ComputerResponse getById(String id);

    String deleteById(String id);



}
