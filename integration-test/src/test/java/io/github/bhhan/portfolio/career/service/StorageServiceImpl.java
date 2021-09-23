package io.github.bhhan.portfolio.career.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

public class StorageServiceImpl implements StorageService{
    @Override
    public String store(MultipartFile file) {
        return file.getOriginalFilename();
    }

    @Override
    public List<String> storeAll(List<MultipartFile> files) {
        return files.stream()
                .map(MultipartFile::getOriginalFilename)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String fileName) {

    }

    @Override
    public void delete(List<String> fileNames) {

    }
}
