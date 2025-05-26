package tech.swahell.mobiliteinternationale.entity;

import jakarta.persistence.Entity;

import java.io.Serializable;

@Entity
public class MobilityOfficer extends User implements Serializable {

    private String department;

    public MobilityOfficer() {
        this.setRole(Role.MOBILITY_OFFICER);
    }

    public MobilityOfficer(String fullName, String email, String password, String department) {
        this.setFullName(fullName);
        this.setEmail(email);
        this.setPassword(password);
        this.setRole(Role.MOBILITY_OFFICER);
        this.department = department;
    }

    // Getters and setters
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "MobilityOfficer{" +
                "id=" + getId() +
                ", fullName='" + getFullName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", department='" + department + '\'' +
                ", role=" + getRole() +
                '}';
    }

}