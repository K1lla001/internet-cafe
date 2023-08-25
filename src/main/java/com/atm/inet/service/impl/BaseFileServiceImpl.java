package com.atm.inet.service.impl;

import com.atm.inet.entity.BaseFile;
import com.atm.inet.service.BaseFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BaseFileServiceImpl implements BaseFileService {

    @Value("${icafe.image-path-url}")
    private String path;

    @Override
    public BaseFile create(MultipartFile multipartFile) {
        if (multipartFile.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "file tidak boleh kosong");

        if (!List.of("image/jpeg", "image/png").contains(multipartFile.getContentType()))
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "content type tidak valid");

        try {
            Path directoryPath = Paths.get(path);
            Files.createDirectories(directoryPath);
            String filename = String.format("%d_%s", System.currentTimeMillis(), multipartFile.getOriginalFilename());
            Path filePath = directoryPath.resolve(filename);
            Files.copy(multipartFile.getInputStream(), filePath);

            return BaseFile.builder()
                    .name(filename)
                    .path(filePath.toString())
                    .size(multipartFile.getSize())
                    .contentType(multipartFile.getContentType())
                    .build();
        } catch (IOException | RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "terjadi kegagalan server");
        }
    }

    @Override
    public List<BaseFile> createBulk(List<MultipartFile> multipartFiles) {
        return multipartFiles.stream().map(this::create).collect(Collectors.toList());
    }

    @Override
    public Resource get(String path) {
        Path filePath = Paths.get(path);
        try {
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "terjadi kegagalan server");
        }
    }
    @Override
    public void delete(String path) {
        try {
            Path filePath = Paths.get(path);
            boolean exists = Files.deleteIfExists(filePath);
            if (!exists) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "file tidak ditemukan");
        } catch (IOException | RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "terjadi kegagalan server");
        }
    }


}
