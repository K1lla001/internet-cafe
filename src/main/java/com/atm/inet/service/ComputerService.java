package com.atm.inet.service;

import com.atm.inet.entity.computer.Computer;
import com.atm.inet.model.common.ComputerSearch;
import com.atm.inet.model.request.ComputerRequest;
import com.atm.inet.model.request.ComputerUpdateRequest;
import com.atm.inet.model.response.ComputerResponse;
import com.atm.inet.model.response.NewComputerResponse;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ComputerService {

    NewComputerResponse save(ComputerRequest request, MultipartFile computerImage);

    Page<ComputerResponse> getAll(Pageable pageable, ComputerSearch computerSearch);
    ComputerResponse updateComputer(ComputerUpdateRequest updateComputer);

    Computer findByTypeId(String id);

    ComputerResponse getById(String id);

    String deleteById(String id);

    Resource downloadComputerImg(String id);



}
