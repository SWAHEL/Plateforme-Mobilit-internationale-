package tech.swahell.mobiliteinternationale.exception;

public class OCRServiceException extends RuntimeException {
    public OCRServiceException(String message) {
        super(message);
    }

    public OCRServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
