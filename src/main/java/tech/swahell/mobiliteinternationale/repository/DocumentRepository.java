package tech.swahell.mobiliteinternationale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.swahell.mobiliteinternationale.entity.Document;
import tech.swahell.mobiliteinternationale.entity.Mobility;
import tech.swahell.mobiliteinternationale.entity.DocumentType;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    // 🔍 Get all documents related to a specific mobility (by entity)
    List<Document> findByMobility(Mobility mobility);

    // 🔍 Get all documents related to a specific mobility (by ID)
    List<Document> findByMobilityId(Long mobilityId);

    // 🔍 Get all documents of a specific type (e.g. RELEVE, ATTESTATION, DIPLOME)
    List<Document> findByType(DocumentType type);

    // 🔍 Get all documents processed or not by OCR
    List<Document> findByOcrExtracted(boolean ocrExtracted);
}
