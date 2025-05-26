package tech.swahell.mobiliteinternationale.dto;

public class StudentRequest {
    private String fullName;
    private String email;
    private String password;
    private String filiere;      // string representation of Filiere enum
    private Long partnerId;
    private String currentUserRole;

    // Getters
    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFiliere() {
        return filiere;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public String getCurrentUserRole() {
        return currentUserRole;
    }

    // Setters
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public void setCurrentUserRole(String currentUserRole) {
        this.currentUserRole = currentUserRole;
    }
}
