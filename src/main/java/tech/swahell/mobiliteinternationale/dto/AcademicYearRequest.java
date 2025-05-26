package tech.swahell.mobiliteinternationale.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AcademicYearRequest {

    @NotBlank(message = "Year label is required")
    private String yearLabel; // e.g., "2023-2024"

    @NotNull(message = "Mobility ID is required")
    private Long mobilityId;

    // ✅ Constructors
    public AcademicYearRequest() {
    }

    public AcademicYearRequest(String yearLabel, Long mobilityId) {
        this.yearLabel = yearLabel;
        this.mobilityId = mobilityId;
    }

    // ✅ Getters and Setters
    public String getYearLabel() {
        return yearLabel;
    }

    public void setYearLabel(String yearLabel) {
        this.yearLabel = yearLabel;
    }

    public Long getMobilityId() {
        return mobilityId;
    }

    public void setMobilityId(Long mobilityId) {
        this.mobilityId = mobilityId;
    }

    @Override
    public String toString() {
        return "AcademicYearRequest{" +
                "yearLabel='" + yearLabel + '\'' +
                ", mobilityId=" + mobilityId +
                '}';
    }
}
