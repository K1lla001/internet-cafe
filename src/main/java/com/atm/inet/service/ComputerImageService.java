package com.atm.inet.service;

import com.atm.inet.entity.computer.Computer;
import com.atm.inet.entity.computer.ComputerImage;
import com.atm.inet.entity.computer.Type;
import com.atm.inet.model.request.ComputerRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ComputerImageService {
    ComputerImage create(Type request, MultipartFile multipartFile);

}
