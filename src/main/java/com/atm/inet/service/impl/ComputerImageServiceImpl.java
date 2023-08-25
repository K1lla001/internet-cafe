package com.atm.inet.service.impl;

import com.atm.inet.entity.BaseFile;
import com.atm.inet.entity.computer.ComputerImage;
import com.atm.inet.entity.computer.Type;
import com.atm.inet.repository.ComputerImageRepository;
import com.atm.inet.service.BaseFileService;
import com.atm.inet.service.ComputerImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ComputerImageServiceImpl implements ComputerImageService {

    private final BaseFileService baseFileService;

    @Override
    public ComputerImage create(Type request, MultipartFile multipartFile) {
        BaseFile baseFile = baseFileService.create(multipartFile);
        return ComputerImage.builder()
                .name(baseFile.getName())
                .contentType(baseFile.getContentType())
                .path(baseFile.getPath())
                .size(baseFile.getSize())
                .type(request)
                .build();
    }
}
