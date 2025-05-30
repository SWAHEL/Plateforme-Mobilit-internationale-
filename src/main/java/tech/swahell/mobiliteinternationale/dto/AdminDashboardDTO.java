package tech.swahell.mobiliteinternationale.dto;

import java.util.Map;

public class AdminDashboardDTO {
    private long totalMobilities;
    private long totalStudents;
    private Map<String, Long> mobilitiesByPartner;
    private Map<String, Long> mobilitiesByFiliere;
    private double overallAverageGrade;
    private Map<String, Long> decisionStats;
    private Map<String, Long> mobilitiesByYear;

    // Getters and Setters
    public long getTotalMobilities() { return totalMobilities; }
    public void setTotalMobilities(long totalMobilities) { this.totalMobilities = totalMobilities; }

    public long getTotalStudents() { return totalStudents; }
    public void setTotalStudents(long totalStudents) { this.totalStudents = totalStudents; }

    public Map<String, Long> getMobilitiesByPartner() { return mobilitiesByPartner; }
    public void setMobilitiesByPartner(Map<String, Long> mobilitiesByPartner) { this.mobilitiesByPartner = mobilitiesByPartner; }

    public Map<String, Long> getMobilitiesByFiliere() { return mobilitiesByFiliere; }
    public void setMobilitiesByFiliere(Map<String, Long> mobilitiesByFiliere) { this.mobilitiesByFiliere = mobilitiesByFiliere; }

    public double getOverallAverageGrade() { return overallAverageGrade; }
    public void setOverallAverageGrade(double overallAverageGrade) { this.overallAverageGrade = overallAverageGrade; }

    public Map<String, Long> getDecisionStats() { return decisionStats; }
    public void setDecisionStats(Map<String, Long> decisionStats) { this.decisionStats = decisionStats; }

    public Map<String, Long> getMobilitiesByYear() { return mobilitiesByYear; }
    public void setMobilitiesByYear(Map<String, Long> mobilitiesByYear) { this.mobilitiesByYear = mobilitiesByYear; }
}
