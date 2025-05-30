package tech.swahell.mobiliteinternationale.dto;

import java.util.Map;

public class PartnerDashboardDTO {
    private long totalStudents;
    private long exchangeCount;
    private long doubleDiplomaCount;
    private Map<String, Long> countByAcademicYear;

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getExchangeCount() {
        return exchangeCount;
    }

    public void setExchangeCount(long exchangeCount) {
        this.exchangeCount = exchangeCount;
    }

    public long getDoubleDiplomaCount() {
        return doubleDiplomaCount;
    }

    public void setDoubleDiplomaCount(long doubleDiplomaCount) {
        this.doubleDiplomaCount = doubleDiplomaCount;
    }

    public Map<String, Long> getCountByAcademicYear() {
        return countByAcademicYear;
    }

    public void setCountByAcademicYear(Map<String, Long> countByAcademicYear) {
        this.countByAcademicYear = countByAcademicYear;
    }
}
