package tech.swahell.mobiliteinternationale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.swahell.mobiliteinternationale.entity.Document;
import tech.swahell.mobiliteinternationale.entity.DocumentType;
import tech.swahell.mobiliteinternationale.entity.Mobility;
import tech.swahell.mobiliteinternationale.exception.DocumentNotFoundException;
import tech.swahell.mobiliteinternationale.exception.MobilityNotFoundException;
import tech.swahell.mobiliteinternationale.repository.DocumentRepository;
import tech.swahell.mobiliteinternationale.repository.MobilityRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final MobilityRepository mobilityRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository, MobilityRepository mobilityRepository) {
        this.documentRepository = documentRepository;
        this.mobilityRepository = mobilityRepository;
    }

    /**
     * ➕ Add a new document linked to a specific mobility
     */
    public Document addDocument(Long mobilityId, DocumentType type, String filePath, boolean ocrExtracted) {
        Mobility mobility = mobilityRepository.findById(mobilityId)
                .orElseThrow(() -> new MobilityNotFoundException("Mobility not found with ID: " + mobilityId));

        Document document = new Document();
        document.setMobility(mobility);
        document.setType(type);
        document.setFilePath(filePath);
        document.setOcrExtracted(ocrExtracted);
        document.setUploadDate(LocalDate.now());

        return documentRepository.save(document);
    }

    /**
     * 📁 Get all documents
     */
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    /**
     * 🔍 Get documents by type
     */
    public List<Document> getDocumentsByType(DocumentType type) {
        return documentRepository.findByType(type);
    }

    /**
     * 🔍 Get documents by mobility ID
     */
    public List<Document> getDocumentsByMobilityId(Long mobilityId) {
        if (!mobilityRepository.existsById(mobilityId)) {
            throw new MobilityNotFoundException("Mobility not found with ID: " + mobilityId);
        }
        return documentRepository.findByMobilityId(mobilityId);
    }

    /**
     * 🔍 Get specific type of document for a mobility
     */
    public List<Document> getDocumentsByMobilityIdAndType(Long mobilityId, DocumentType type) {
        if (!mobilityRepository.existsById(mobilityId)) {
            throw new MobilityNotFoundException("Mobility not found with ID: " + mobilityId);
        }
        return documentRepository.findByMobilityIdAndType(mobilityId, type);
    }

    /**
     * ❌ Delete a document by ID
     */
    public void deleteDocument(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new DocumentNotFoundException("Document not found with ID: " + id);
        }
        documentRepository.deleteById(id);
    }

    /**
     * 🔍 Find a single document by ID
     */
    public Document getDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found with ID: " + id));
    }
}
