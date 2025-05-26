package tech.swahell.mobiliteinternationale.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tech.swahell.mobiliteinternationale.entity.SemesterType;

public class SemesterRequest {

    @NotBlank(message = "Semester label is required")
    private String label; // e.g., "S1", "Fall 2024", "PFE"

    @NotNull(message = "Semester type is required")
    private SemesterType type; // NORMAL or PFE

    @NotNull(message = "Academic Year ID is required")
    private Long academicYearId;

    // ✅ Constructors
    public SemesterRequest() {}

    public SemesterRequest(String label, SemesterType type, Long academicYearId) {
        this.label = label;
        this.type = type;
        this.academicYearId = academicYearId;
    }

    // ✅ Getters and Setters
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public SemesterType getType() {
        return type;
    }

    public void setType(SemesterType type) {
        this.type = type;
    }

    public Long getAcademicYearId() {
        return academicYearId;
    }

    public void setAcademicYearId(Long academicYearId) {
        this.academicYearId = academicYearId;
    }

    @Override
    public String toString() {
        return "SemesterRequest{" +
                "label='" + label + '\'' +
                ", type=" + type +
                ", academicYearId=" + academicYearId +
                '}';
    }
}
