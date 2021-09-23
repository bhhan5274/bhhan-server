package io.github.bhhan.portfolio.career.web.storage;

import io.github.bhhan.portfolio.career.StorageProperties;
import io.github.bhhan.portfolio.career.service.storage.FileSystemStorageServiceImpl;
import org.apache.tika.Tika;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/")
@ResponseBody
public class FileDownloadApi {
    private final FileSystemStorageServiceImpl storageService;
    private static StorageProperties storageProperties;
    private Tika tika = new Tika();

    public FileDownloadApi(FileSystemStorageServiceImpl storageService, StorageProperties storageProperties) {
        this.storageService = storageService;
        FileDownloadApi.storageProperties = storageProperties;
    }

    public static String getFileDownloadUrl(String fileName){
        return String.format("%s/career/files/%s", storageProperties.getGatewayUrl(), fileName);
    }

    @GetMapping("/files/{fileName}")
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName){
        String mimeType;
        Resource resource = storageService.loadAsResource(fileName);

        try{
            mimeType = tika.detect(resource.getFile());
        }catch(Exception e){
            throw new FileDownloadErrorException("파일 다운로드에 실패했습니다.");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, mimeType)
                .body(resource);
    }
}
