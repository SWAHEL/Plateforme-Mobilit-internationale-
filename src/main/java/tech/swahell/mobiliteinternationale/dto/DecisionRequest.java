package tech.swahell.mobiliteinternationale.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tech.swahell.mobiliteinternationale.entity.CommissionRole;
import tech.swahell.mobiliteinternationale.entity.DecisionVerdict;

/**
 * DTO for creating or updating a Decision entity.
 */
public class DecisionRequest {

    @NotNull(message = "Mobility ID is required")
    private Long mobilityId;

    @NotBlank(message = "Mention is required")
    private String mention;

    @NotBlank(message = "PV path is required")
    private String pvPath;

    @NotNull(message = "Verdict is required")
    private DecisionVerdict verdict;

    @NotNull(message = "Made by role is required")
    private CommissionRole madeByRole;

    @NotBlank(message = "Made by name is required")
    private String madeBy;

    private String comment;

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

    public DecisionVerdict getVerdict() {
        return verdict;
    }

    public void setVerdict(DecisionVerdict verdict) {
        this.verdict = verdict;
    }

    public CommissionRole getMadeByRole() {
        return madeByRole;
    }

    public void setMadeByRole(CommissionRole madeByRole) {
        this.madeByRole = madeByRole;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
