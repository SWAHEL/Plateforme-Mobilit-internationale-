package tech.swahell.mobiliteinternationale.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class AcademicYear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String yearLabel; // Example: "2023-2024"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mobility_id", nullable = false)
    @JsonIgnore
    private Mobility mobility;

    @OneToMany(mappedBy = "academicYear", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Semester> semesters;

    // ✅ Default constructor (required by JPA)
    public AcademicYear() {
    }

    // ✅ Full constructor
    public AcademicYear(String yearLabel, Mobility mobility, List<Semester> semesters) {
        this.yearLabel = yearLabel;
        this.mobility = mobility;
        this.semesters = semesters;
    }

    // ✅ Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYearLabel() {
        return yearLabel;
    }

    public void setYearLabel(String yearLabel) {
        this.yearLabel = yearLabel;
    }

    public Mobility getMobility() {
        return mobility;
    }

    public void setMobility(Mobility mobility) {
        this.mobility = mobility;
    }

    public List<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<Semester> semesters) {
        this.semesters = semesters;
    }

    // ✅ toString() for debugging (avoid fetching lazy relationships directly)
    @Override
    public String toString() {
        return "AcademicYear{" +
                "id=" + id +
                ", yearLabel='" + yearLabel + '\'' +
                ", mobilityId=" + (mobility != null ? mobility.getId() : null) +
                ", semestersCount=" + (semesters != null ? semesters.size() : 0) +
                '}';
    }

    // ✅ Optional: equals & hashCode if needed in collections (not mandatory)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AcademicYear)) return false;
        AcademicYear that = (AcademicYear) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
