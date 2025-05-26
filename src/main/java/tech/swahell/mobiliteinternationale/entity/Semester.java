package tech.swahell.mobiliteinternationale.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label; // Example: "S1", "S2", "Fall 2023", etc.

    @Enumerated(EnumType.STRING)
    private SemesterType type; // NORMAL or PFE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_year_id", nullable = false)
    @JsonIgnore
    private AcademicYear academicYear;

    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Module> modules;

    // ✅ Default constructor
    public Semester() {
    }

    // ✅ Full constructor
    public Semester(String label, SemesterType type, AcademicYear academicYear, List<Module> modules) {
        this.label = label;
        this.type = type;
        this.academicYear = academicYear;
        this.modules = modules;
    }

    // ✅ Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    // ✅ toString (safe version)
    @Override
    public String toString() {
        return "Semester{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", type=" + type +
                ", academicYearId=" + (academicYear != null ? academicYear.getId() : null) +
                ", modulesCount=" + (modules != null ? modules.size() : 0) +
                '}';
    }

    // ✅ equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Semester)) return false;
        Semester semester = (Semester) o;
        return Objects.equals(id, semester.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
