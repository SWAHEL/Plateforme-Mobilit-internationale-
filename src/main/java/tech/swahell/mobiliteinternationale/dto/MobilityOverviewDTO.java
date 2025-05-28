package tech.swahell.mobiliteinternationale.dto;

import tech.swahell.mobiliteinternationale.entity.MobilityType;

import java.time.LocalDate;

public class MobilityOverviewDTO {

    private Long mobilityId;
    private String studentFullName;
    private String filiere;
    private String partnerName;
    private MobilityType type;
    private String program;
    private String status;
    private Double averageGrade;
    private String mention;
    private LocalDate startDate;
    private LocalDate endDate;

    // Getters and Setters

    public Long getMobilityId() {
        return mobilityId;
    }

    public void setMobilityId(Long mobilityId) {
        this.mobilityId = mobilityId;
    }

    public String getStudentFullName() {
        return studentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        this.studentFullName = studentFullName;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public MobilityType getType() {
        return type;
    }

    public void setType(MobilityType type) {
        this.type = type;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
