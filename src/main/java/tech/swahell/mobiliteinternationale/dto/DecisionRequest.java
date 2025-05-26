package tech.swahell.mobiliteinternationale.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DecisionRequest {

    @NotNull(message = "Mobility ID is required")
    private Long mobilityId;

    @NotBlank(message = "Mention is required")
    private String mention;

    @NotBlank(message = "PV path is required")
    private String pvPath;

    // Getters and Setters

    public Long getMobilityId() {
        return mobilityId;
    }

    public void setMobilityId(Long mobilityId) {
        this.mobilityId = mobilityId;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public String getPvPath() {
        return pvPath;
    }

    public void setPvPath(String pvPath) {
        this.pvPath = pvPath;
    }
}
