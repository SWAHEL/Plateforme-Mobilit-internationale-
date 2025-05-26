package tech.swahell.mobiliteinternationale.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class DocumentRequest {

    @NotNull(message = "Mobility ID is required")
    private Long mobilityId;

    @NotBlank(message = "Document type is required")
    private String type;

    @NotBlank(message = "File path is required")
    private String filePath;

    private boolean ocrExtracted = false;

    private LocalDate uploadDate = LocalDate.now(); // Default to today

    // Getters and Setters
    public Long getMobilityId() {
        return mobilityId;
    }

    public void setMobilityId(Long mobilityId) {
        this.mobilityId = mobilityId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isOcrExtracted() {
        return ocrExtracted;
    }

    public void setOcrExtracted(boolean ocrExtracted) {
        this.ocrExtracted = ocrExtracted;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }
}
