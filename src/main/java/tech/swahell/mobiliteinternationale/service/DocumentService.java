package tech.swahell.mobiliteinternationale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.swahell.mobiliteinternationale.entity.Document;
import tech.swahell.mobiliteinternationale.entity.DocumentType;
import tech.swahell.mobiliteinternationale.entity.Mobility;
import tech.swahell.mobiliteinternationale.exception.DocumentNotFoundException;
import tech.swahell.mobiliteinternationale.exception.MobilityNotFoundException;
import tech.swahell.mobiliteinternationale.repository.DocumentRepository;
import tech.swahell.mobiliteinternationale.repository.MobilityRepository;

import java.io.IOException;
import java.nio.file.*;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.Formatter;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final MobilityRepository mobilityRepository;

    private static final String UPLOAD_DIR = "uploads";

    @Autowired
    public DocumentService(DocumentRepository documentRepository, MobilityRepository mobilityRepository) {
        this.documentRepository = documentRepository;
        this.mobilityRepository = mobilityRepository;
    }

    /**
     * 📁 Upload and save a PDF document
     */
    public Document uploadDocument(MultipartFile file, DocumentType type, Long mobilityId) {
        Mobility mobility = mobilityRepository.findById(mobilityId)
                .orElseThrow(() -> new MobilityNotFoundException("Mobility not found with ID: " + mobilityId));

        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        try {
            // Ensure directory exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            Files.createDirectories(uploadPath);

            // Generate unique filename
            String extension = getExtension(file.getOriginalFilename());
            String filename = UUID.randomUUID() + extension;
            Path filePath = uploadPath.resolve(filename);

            // Save file to disk
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Calculate SHA-256 file hash
            String hash = calculateFileHash(file);

            // Create Document entity
            Document document = new Document();
            document.setMobility(mobility);
            document.setType(type);
            document.setFilePath(filePath.toString());
            document.setOriginalFilename(file.getOriginalFilename());
            document.setContentType(file.getContentType());
            document.setUploadDate(LocalDate.now());
            document.setOcrExtracted(false);
            document.setFileHash(hash);

            return documentRepository.save(document);

        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }
    }

    // 📎 Helper: get file extension
    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return ".pdf";
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    // 🧮 Helper: calculate SHA-256 hash of the file
    private String calculateFileHash(MultipartFile file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(file.getBytes());
            Formatter formatter = new Formatter();
            for (byte b : hashBytes) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to compute file hash", e);
        }
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public List<Document> getDocumentsByType(DocumentType type) {
        return documentRepository.findByType(type);
    }

    public List<Document> getDocumentsByMobilityId(Long mobilityId) {
        if (!mobilityRepository.existsById(mobilityId)) {
            throw new MobilityNotFoundException("Mobility not found with ID: " + mobilityId);
        }
        return documentRepository.findByMobilityId(mobilityId);
    }

    public List<Document> getDocumentsByMobilityIdAndType(Long mobilityId, DocumentType type) {
        if (!mobilityRepository.existsById(mobilityId)) {
            throw new MobilityNotFoundException("Mobility not found with ID: " + mobilityId);
        }
        return documentRepository.findByMobilityIdAndType(mobilityId, type);
    }

    public void deleteDocument(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new DocumentNotFoundException("Document not found with ID: " + id);
        }
        documentRepository.deleteById(id);
    }

    public Document getDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found with ID: " + id));
    }

    /**
     * 💾 Save (update) an existing document
     */
    public Document save(Document document) {
        return documentRepository.save(document);
    }
}
