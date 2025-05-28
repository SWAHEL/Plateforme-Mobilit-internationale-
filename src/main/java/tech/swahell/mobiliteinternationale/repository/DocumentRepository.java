package tech.swahell.mobiliteinternationale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.swahell.mobiliteinternationale.entity.Document;
import tech.swahell.mobiliteinternationale.entity.DocumentType;
import tech.swahell.mobiliteinternationale.entity.Mobility;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    // 🔍 Get all documents for a given Mobility entity
    List<Document> findByMobility(Mobility mobility);

    // 🔍 Get all documents for a given Mobility ID
    List<Document> findByMobilityId(Long mobilityId);

    // 🔍 Get documents by type (e.g., TRANSCRIPT, ATTESTATION_REUSSITE)
    List<Document> findByType(DocumentType type);

    // 🔍 Get documents filtered by OCR extraction status
    List<Document> findByOcrExtracted(boolean ocrExtracted);

    // 🔍 Get documents by mobility and type
    List<Document> findByMobilityIdAndType(Long mobilityId, DocumentType type);

    // 🆕 Optional: Find document by SHA-256 hash to prevent duplicates
    Optional<Document> findByFileHash(String fileHash);
}
