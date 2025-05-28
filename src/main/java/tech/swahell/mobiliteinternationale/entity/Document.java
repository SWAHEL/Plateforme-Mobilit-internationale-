package tech.swahell.mobiliteinternationale.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Document implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType type; // e.g., TRANSCRIPT, ATTESTATION_REUSSITE

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String originalFilename; // new

    @Column(nullable = false)
    private String contentType; // new (e.g., application/pdf)

    private boolean ocrExtracted;

    private LocalDate uploadDate;

    @Column(columnDefinition = "TEXT")
    private String rawOcrText; // new – optional (raw OCR result)

    @Column(length = 64)
    private String fileHash; // new – optional (e.g., SHA-256)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mobility_id", nullable = false)
    private Mobility mobility;

    // 🧱 Constructors
    public Document() {}

    public Document(DocumentType type, String filePath, String originalFilename, String contentType,
                    boolean ocrExtracted, LocalDate uploadDate, Mobility mobility) {
        this.type = type;
        this.filePath = filePath;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.ocrExtracted = ocrExtracted;
        this.uploadDate = uploadDate;
        this.mobility = mobility;
    }

    // 🔄 Getters and Setters

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

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
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

    public String getRawOcrText() {
        return rawOcrText;
    }

    public void setRawOcrText(String rawOcrText) {
        this.rawOcrText = rawOcrText;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
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
                ", originalFilename='" + originalFilename + '\'' +
                ", contentType='" + contentType + '\'' +
                ", ocrExtracted=" + ocrExtracted +
                ", uploadDate=" + uploadDate +
                ", fileHash=" + fileHash +
                '}';
    }
}
