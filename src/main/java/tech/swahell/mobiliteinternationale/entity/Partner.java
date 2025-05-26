package tech.swahell.mobiliteinternationale.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Partner extends User implements Serializable {

    private String universityName;
    private String country;
    private String gradingScale;

    @OneToMany(mappedBy = "partner")
    private List<Student> assignedStudents;

    public Partner() {
        this.setRole(Role.PARTNER);
    }

    public Partner(String fullName, String email, String password, String universityName, String country, String gradingScale) {
        this.setFullName(fullName);
        this.setEmail(email);
        this.setPassword(password);
        this.setRole(Role.PARTNER);
        this.universityName = universityName;
        this.country = country;
        this.gradingScale = gradingScale;
    }

    // Getters and setters
    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGradingScale() {
        return gradingScale;
    }

    public void setGradingScale(String gradingScale) {
        this.gradingScale = gradingScale;
    }

    public List<Student> getAssignedStudents() {
        return assignedStudents;
    }

    public void setAssignedStudents(List<Student> assignedStudents) {
        this.assignedStudents = assignedStudents;
    }

    @Override
    public String toString() {
        return "Partner{" +
                "id=" + getId() +
                ", fullName='" + getFullName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", universityName='" + universityName + '\'' +
                ", country='" + country + '\'' +
                ", gradingScale='" + gradingScale + '\'' +
                ", role=" + getRole() +
                '}';
    }

}