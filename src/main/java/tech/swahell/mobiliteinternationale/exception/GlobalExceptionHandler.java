package tech.swahell.mobiliteinternationale.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStudentNotFound(StudentNotFoundException ex) {
        return buildErrorResponse("STUDENT_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PartnerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePartnerNotFound(PartnerNotFoundException ex) {
        return buildErrorResponse("PARTNER_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MobilityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMobilityNotFound(MobilityNotFoundException ex) {
        return buildErrorResponse("MOBILITY_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDocumentNotFound(DocumentNotFoundException ex) {
        return buildErrorResponse("DOCUMENT_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DecisionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDecisionNotFound(DecisionNotFoundException ex) {
        return buildErrorResponse("DECISION_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MobilityOfficerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMobilityOfficerNotFound(MobilityOfficerNotFoundException ex) {
        return buildErrorResponse("MOBILITY_OFFICER_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CoordinatorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCoordinatorNotFound(CoordinatorNotFoundException ex) {
        return buildErrorResponse("COORDINATOR_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OCRServiceException.class)
    public ResponseEntity<ErrorResponse> handleOCRServiceError(OCRServiceException ex) {
        return buildErrorResponse("OCR_SERVICE_ERROR", ex.getMessage(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedAccessException ex) {
        return buildErrorResponse("UNAUTHORIZED", ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ModuleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleModuleNotFound(ModuleNotFoundException ex) {
        return buildErrorResponse("MODULE_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SemesterNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSemesterNotFound(SemesterNotFoundException ex) {
        return buildErrorResponse("SEMESTER_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AcademicYearNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAcademicYearNotFound(AcademicYearNotFoundException ex) {
        return buildErrorResponse("ACADEMIC_YEAR_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return buildErrorResponse("TYPE_MISMATCH", "Invalid value: " + ex.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex) {
        return buildErrorResponse("BAD_REQUEST", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoSuchElementException ex) {
        return buildErrorResponse("NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return buildErrorResponse("INTERNAL_ERROR", "Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String code, String message, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(code, message);
        return new ResponseEntity<>(errorResponse, status);
    }
}
