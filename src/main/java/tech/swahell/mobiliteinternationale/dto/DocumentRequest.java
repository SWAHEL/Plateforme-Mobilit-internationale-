package tech.swahell.mobiliteinternationale.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DocumentRequest {

    @NotNull(message = "Mobility ID is required")
    private Long mobilityId;

    @NotBlank(message = "Document type is required")
    private String type; // Should match values in DocumentType enum

    @NotBlank(message = "File path is required")
    private String filePath;

    private boolean ocrExtracted = false;

    // Constructors
    public DocumentRequest() {}

    public DocumentRequest(Long mobilityId, String type, String filePath, boolean ocrExtracted) {
        this.mobilityId = mobilityId;
        this.type = type;
        this.filePath = filePath;
        this.ocrExtracted = ocrExtracted;
    }

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
}
