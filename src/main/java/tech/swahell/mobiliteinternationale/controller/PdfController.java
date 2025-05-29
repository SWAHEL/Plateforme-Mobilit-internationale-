package tech.swahell.mobiliteinternationale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tech.swahell.mobiliteinternationale.entity.Decision;
import tech.swahell.mobiliteinternationale.service.AttestationGeneratorService;
import tech.swahell.mobiliteinternationale.service.DecisionService;
import tech.swahell.mobiliteinternationale.service.PVGeneratorService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    private final PVGeneratorService pvGeneratorService;
    private final AttestationGeneratorService attestationGeneratorService;
    private final DecisionService decisionService;

    @Autowired
    public PdfController(PVGeneratorService pvGeneratorService,
                         AttestationGeneratorService attestationGeneratorService,
                         DecisionService decisionService) {
        this.pvGeneratorService = pvGeneratorService;
        this.attestationGeneratorService = attestationGeneratorService;
        this.decisionService = decisionService;
    }

    // ✅ Générer le PV dynamiquement
    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN', 'SYSTEM_ADMIN', 'COORDINATOR')")
    @GetMapping("/pv/{mobilityId}")
    public ResponseEntity<ByteArrayResource> downloadPV(@PathVariable Long mobilityId) throws IOException {
        Decision decision = decisionService.getDecisionByMobility(mobilityId)
                .orElseThrow(() -> new RuntimeException("Aucune décision trouvée pour cette mobilité."));

        String pdfPath = pvGeneratorService.generatePVForDecision(decision);
        File file = new File(pdfPath);
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(file.toPath()));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pv_mobility_" + mobilityId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(file.length())
                .body(resource);
    }

    // ✅ Générer l’attestation dynamiquement
    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN', 'SYSTEM_ADMIN', 'COORDINATOR')")
    @GetMapping("/attestation/{mobilityId}")
    public ResponseEntity<byte[]> downloadAttestation(@PathVariable Long mobilityId) {
        byte[] pdfContent = attestationGeneratorService.generateAttestation(mobilityId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=attestation_mobility_" + mobilityId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfContent);
    }

    // 📁 Télécharger le PV depuis le chemin stocké en base
    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN', 'SYSTEM_ADMIN', 'COORDINATOR')")
    @GetMapping("/pv-file/{mobilityId}")
    public ResponseEntity<byte[]> downloadStoredPv(@PathVariable Long mobilityId) throws IOException {
        Decision decision = decisionService.getDecisionByMobility(mobilityId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Aucune décision trouvée"));

        File file = new File(decision.getPvPath());
        if (!file.exists()) throw new ResponseStatusException(NOT_FOUND, "Fichier PV introuvable");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentType(MediaType.APPLICATION_PDF)
                .body(Files.readAllBytes(file.toPath()));
    }

    // 📁 Télécharger l’attestation depuis le chemin stocké en base
    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN', 'SYSTEM_ADMIN', 'COORDINATOR')")
    @GetMapping("/attestation-file/{mobilityId}")
    public ResponseEntity<byte[]> downloadStoredAttestation(@PathVariable Long mobilityId) throws IOException {
        Decision decision = decisionService.getDecisionByMobility(mobilityId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Aucune décision trouvée"));

        File file = new File(decision.getAttestationPath());
        if (!file.exists()) throw new ResponseStatusException(NOT_FOUND, "Fichier attestation introuvable");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentType(MediaType.APPLICATION_PDF)
                .body(Files.readAllBytes(file.toPath()));
    }
}
