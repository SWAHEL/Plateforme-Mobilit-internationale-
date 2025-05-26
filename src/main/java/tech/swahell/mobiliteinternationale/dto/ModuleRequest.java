package tech.swahell.mobiliteinternationale.dto;

import jakarta.validation.constraints.*;

public class ModuleRequest {

    @NotBlank(message = "Module name is required")
    private String name;

    @NotNull(message = "Original grade is required")
    @DecimalMin(value = "0.0", message = "Original grade must be >= 0")
    @DecimalMax(value = "20.0", message = "Original grade must be <= 20")
    private Double originalGrade;

    @NotNull(message = "ECTS is required")
    @Min(value = 1, message = "ECTS must be at least 1")
    private Integer ects;

    private boolean isPfe;

    // ✅ Constructors
    public ModuleRequest() {}

    public ModuleRequest(String name, Double originalGrade, Integer ects, boolean isPfe) {
        this.name = name;
        this.originalGrade = originalGrade;
        this.ects = ects;
        this.isPfe = isPfe;
    }

    // ✅ Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getOriginalGrade() {
        return originalGrade;
    }

    public void setOriginalGrade(Double originalGrade) {
        this.originalGrade = originalGrade;
    }

    public Integer getEcts() {
        return ects;
    }

    public void setEcts(Integer ects) {
        this.ects = ects;
    }

    public boolean isPfe() {
        return isPfe;
    }

    public void setPfe(boolean pfe) {
        isPfe = pfe;
    }

    @Override
    public String toString() {
        return "ModuleRequest{" +
                "name='" + name + '\'' +
                ", originalGrade=" + originalGrade +
                ", ects=" + ects +
                ", isPfe=" + isPfe +
                '}';
    }
}
