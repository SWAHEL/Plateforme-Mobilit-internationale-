package tech.swahell.mobiliteinternationale.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Document implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private DocumentType type;

    private String filePath;
    private boolean ocrExtracted;
    private LocalDate uploadDate;

    @ManyToOne
    @JoinColumn(name = "mobility_id")
    private Mobility mobility;

    public Document() {}

    public Document(DocumentType type, String filePath, boolean ocrExtracted, LocalDate uploadDate, Mobility mobility) {
        this.type = type;
        this.filePath = filePath;
        this.ocrExtracted = ocrExtracted;
        this.uploadDate = uploadDate;
        this.mobility = mobility;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isOcrExtracted() {
        return ocrExtracted;
    }

    public void setOcrExtracted(boolean ocrExtracted) {
        this.ocrExtracted = ocrExtracted;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Mobility getMobility() {
        return mobility;
    }

    public void setMobility(Mobility mobility) {
        this.mobility = mobility;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", type=" + type +
                ", filePath='" + filePath + '\'' +
                ", ocrExtracted=" + ocrExtracted +
                ", uploadDate=" + uploadDate +
                '}';
    }
}