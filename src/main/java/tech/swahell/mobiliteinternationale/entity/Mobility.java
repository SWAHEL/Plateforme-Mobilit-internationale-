package tech.swahell.mobiliteinternationale.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Mobility implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String program;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private MobilityType type;

    @Enumerated(EnumType.STRING)
    private MobilityStatus status;

    @OneToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;

    @OneToMany(mappedBy = "mobility", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;

    @OneToOne(mappedBy = "mobility", cascade = CascadeType.ALL)
    private Decision decision;

    // ✅ Academic years for structured modules
    @OneToMany(mappedBy = "mobility", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AcademicYear> academicYears;

    // Constructors
    public Mobility() {}

    public Mobility(String program, LocalDate startDate, LocalDate endDate, MobilityType type, MobilityStatus status, Student student) {
        this.program = program;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.status = status;
        this.student = student;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
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

    public MobilityType getType() {
        return type;
    }

    public void setType(MobilityType type) {
        this.type = type;
    }

    public MobilityStatus getStatus() {
        return status;
    }

    public void setStatus(MobilityStatus status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    public List<AcademicYear> getAcademicYears() {
        return academicYears;
    }

    public void setAcademicYears(List<AcademicYear> academicYears) {
        this.academicYears = academicYears;
    }

    @Override
    public String toString() {
        return "Mobility{" +
                "id=" + id +
                ", program='" + program + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", type=" + type +
                ", status=" + status +
                ", academicYears=" + (academicYears != null ? academicYears.size() : 0) +
                '}';
    }
}
