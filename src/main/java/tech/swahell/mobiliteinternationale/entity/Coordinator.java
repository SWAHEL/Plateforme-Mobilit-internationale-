package tech.swahell.mobiliteinternationale.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Coordinator extends User implements Serializable {

    @Enumerated(EnumType.STRING)
    private Filiere filiere;

    public Coordinator() {
        this.setRole(Role.COORDINATOR);
    }

    public Coordinator(String fullName, String email, String password, Filiere filiere) {
        this.setFullName(fullName);
        this.setEmail(email);
        this.setPassword(password);
        this.setRole(Role.COORDINATOR);
        this.filiere = filiere;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    @Override
    public String toString() {
        return "Coordinator{" +
                "id=" + getId() +
                ", fullName='" + getFullName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", filiere=" + filiere +
                ", role=" + getRole() +
                '}';
    }
}
