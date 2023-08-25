package com.atm.inet.service;

import com.atm.inet.entity.BaseFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BaseFileService {

    BaseFile create(MultipartFile multipartFile);
    List<BaseFile> createBulk(List<MultipartFile> multipartFiles);
    Resource get(String path);
    void delete(String path);


}
