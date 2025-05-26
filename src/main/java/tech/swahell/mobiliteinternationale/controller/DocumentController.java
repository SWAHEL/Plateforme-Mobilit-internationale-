package tech.swahell.mobiliteinternationale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.swahell.mobiliteinternationale.dto.DocumentRequest;
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
     * 📄 Upload a document related to a mobility
     */
    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(@RequestBody DocumentRequest request) {
        Document document = documentService.addDocument(
                request.getMobilityId(),
                DocumentType.valueOf(request.getType().toUpperCase()),
                request.getFilePath(),
                request.isOcrExtracted()
        );
        return ResponseEntity.ok(document);
    }

    /**
     * 📋 Get all documents
     */
    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }

    /**
     * 🔍 Get documents by mobility ID
     */
    @GetMapping("/mobility/{mobilityId}")
    public ResponseEntity<List<Document>> getByMobility(@PathVariable Long mobilityId) {
        return ResponseEntity.ok(documentService.getDocumentsByMobilityId(mobilityId));
    }

    /**
     * 🔍 Get documents by type
     */
    @GetMapping("/type")
    public ResponseEntity<List<Document>> getByType(@RequestParam String type) {
        DocumentType docType = DocumentType.valueOf(type.toUpperCase());
        return ResponseEntity.ok(documentService.getDocumentsByType(docType));
    }

    /**
     * ❌ Delete document
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}
