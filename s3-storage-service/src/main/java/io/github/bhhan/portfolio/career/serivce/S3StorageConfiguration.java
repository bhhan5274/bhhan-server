package io.github.bhhan.portfolio.career.serivce;

import com.amazonaws.services.s3.AmazonS3Client;
import io.github.bhhan.portfolio.career.serivce.storage.S3StorageServiceImpl;
import io.github.bhhan.portfolio.career.serivce.storage.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class S3StorageConfiguration {
    private final AmazonS3Client amazonS3Client;

    @Bean
    public S3Uploader s3Uploader(){
        return new S3Uploader(amazonS3Client);
    }

    @Bean
    public S3StorageServiceImpl storageService(S3Uploader s3Uploader){
        return new S3StorageServiceImpl(s3Uploader);
    }
}
