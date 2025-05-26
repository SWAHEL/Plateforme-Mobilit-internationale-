package tech.swahell.mobiliteinternationale.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Student extends User implements Serializable {

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)

    private Mobility mobility;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    @JsonIgnore
    private Partner partner;

    @Enumerated(EnumType.STRING)
    private Filiere filiere;

    public Student() {
        this.setRole(Role.STUDENT);
    }

    public Student(String fullName, String email, String password, Partner partner, Filiere filiere) {
        this.setFullName(fullName);
        this.setEmail(email);
        this.setPassword(password);
        this.setRole(Role.STUDENT);
        this.partner = partner;
        this.filiere = filiere;
    }

    public Mobility getMobility() {
        return mobility;
    }

    public void setMobility(Mobility mobility) {
        this.mobility = mobility;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", fullName='" + getFullName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", filiere=" + filiere +
                ", role=" + getRole() +
                '}';
    }
}
