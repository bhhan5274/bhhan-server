package io.github.bhhan.portfolio.career.web.storage;

public class FileDownloadErrorException extends RuntimeException{
    public FileDownloadErrorException(String message) {
        super(message);
    }

    public FileDownloadErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
