package io.github.bhhan.portfolio.career;

import io.github.bhhan.portfolio.career.service.storage.FileSystemStorageServiceImpl;
import io.github.bhhan.portfolio.career.web.storage.FileDownloadApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(StorageProperties.class)
public class FileSystemStorageConfiguration {
    @Bean
    public FileSystemStorageServiceImpl storageService(StorageProperties storageProperties){
        return new FileSystemStorageServiceImpl(storageProperties);
    }

    @Bean
    public FileDownloadApi fileDownloadApi(FileSystemStorageServiceImpl storageService, StorageProperties storageProperties){
        return new FileDownloadApi(storageService, storageProperties);
    }
}
