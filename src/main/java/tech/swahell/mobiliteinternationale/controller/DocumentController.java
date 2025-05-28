package tech.swahell.mobiliteinternationale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.swahell.mobiliteinternationale.entity.Document;
import tech.swahell.mobiliteinternationale.entity.DocumentType;
import tech.swahell.mobiliteinternationale.service.DocumentService;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * 📄 Upload a PDF document related to a mobility
     */
    @PreAuthorize("hasAnyRole('PARTNER', 'SCHOOL_ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type,
            @RequestParam("mobilityId") Long mobilityId) {

        DocumentType docType = DocumentType.valueOf(type.toUpperCase());

        if (docType != DocumentType.TRANSCRIPT && docType != DocumentType.ATTESTATION_REUSSITE) {
            return ResponseEntity.badRequest().body(null); // Only allowed types
        }

        Document savedDocument = documentService.uploadDocument(file, docType, mobilityId);
        return ResponseEntity.ok(savedDocument);
    }

    /**
     * 📋 Get all documents
     */
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','SCHOOL_ADMIN', 'MOBILITY_OFFICER')")
    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }

    /**
     * 🔍 Get documents by mobility ID
     */
    @PreAuthorize("hasAnyRole('STUDENT', 'PARTNER', 'SCHOOL_ADMIN', 'MOBILITY_OFFICER')")
    @GetMapping("/mobility/{mobilityId}")
    public ResponseEntity<List<Document>> getByMobility(@PathVariable Long mobilityId) {
        return ResponseEntity.ok(documentService.getDocumentsByMobilityId(mobilityId));
    }

    /**
     * 🔍 Get documents by type
     */
    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN', 'MOBILITY_OFFICER')")
    @GetMapping("/type")
    public ResponseEntity<List<Document>> getByType(@RequestParam String type) {
        DocumentType docType = DocumentType.valueOf(type.toUpperCase());
        return ResponseEntity.ok(documentService.getDocumentsByType(docType));
    }

    /**
     * 🔍 Get documents by mobility and type
     */
    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN', 'MOBILITY_OFFICER')")
    @GetMapping("/mobility/{mobilityId}/type")
    public ResponseEntity<List<Document>> getByMobilityAndType(@PathVariable Long mobilityId,
                                                               @RequestParam String type) {
        DocumentType docType = DocumentType.valueOf(type.toUpperCase());
        return ResponseEntity.ok(documentService.getDocumentsByMobilityIdAndType(mobilityId, docType));
    }

    /**
     * ❌ Delete document
     */
    @PreAuthorize("hasRole('SCHOOL_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}
