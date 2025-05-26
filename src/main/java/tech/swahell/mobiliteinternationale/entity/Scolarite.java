package tech.swahell.mobiliteinternationale.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Scolarite extends User implements Serializable {

    private String office; // optional: location or department name

    public Scolarite() {
        this.setRole(Role.SCHOOL_ADMIN);
    }

    public Scolarite(String fullName, String email, String password, String office) {
        this.setFullName(fullName);
        this.setEmail(email);
        this.setPassword(password);
        this.setRole(Role.SCHOOL_ADMIN);
        this.office = office;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    @Override
    public String toString() {
        return "Scolarite{" +
                "id=" + getId() +
                ", fullName='" + getFullName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", office='" + office + '\'' +
                ", role=" + getRole() +
                '}';
    }
}
