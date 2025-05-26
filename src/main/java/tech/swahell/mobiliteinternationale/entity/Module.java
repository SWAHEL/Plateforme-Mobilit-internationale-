package tech.swahell.mobiliteinternationale.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "modules")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double originalGrade;

    private Double convertedGrade;

    private Integer ects;

    // Optional: Mark if this module is a PFE
    private boolean isPfe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    @JsonIgnore
    private Semester semester;

    // ✅ Default constructor
    public Module() {
    }

    // ✅ Full constructor
    public Module(String name, Double originalGrade, Double convertedGrade, Integer ects, boolean isPfe, Semester semester) {
        this.name = name;
        this.originalGrade = originalGrade;
        this.convertedGrade = convertedGrade;
        this.ects = ects;
        this.isPfe = isPfe;
        this.semester = semester;
    }

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Double getConvertedGrade() {
        return convertedGrade;
    }

    public void setConvertedGrade(Double convertedGrade) {
        this.convertedGrade = convertedGrade;
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

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    // ✅ toString
    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", originalGrade=" + originalGrade +
                ", convertedGrade=" + convertedGrade +
                ", ects=" + ects +
                ", isPfe=" + isPfe +
                ", semesterId=" + (semester != null ? semester.getId() : null) +
                '}';
    }

    // ✅ equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Module)) return false;
        Module module = (Module) o;
        return Objects.equals(id, module.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
