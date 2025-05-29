package tech.swahell.mobiliteinternationale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.swahell.mobiliteinternationale.dto.DecisionRequest;
import tech.swahell.mobiliteinternationale.entity.Decision;
import tech.swahell.mobiliteinternationale.entity.Mobility;
import tech.swahell.mobiliteinternationale.exception.DecisionNotFoundException;
import tech.swahell.mobiliteinternationale.exception.MobilityNotFoundException;
import tech.swahell.mobiliteinternationale.repository.DecisionRepository;
import tech.swahell.mobiliteinternationale.repository.MobilityRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DecisionService {

    private final DecisionRepository decisionRepository;
    private final MobilityRepository mobilityRepository;
    private final PVGeneratorService pvGeneratorService;
    private final AttestationGeneratorService attestationGeneratorService;

    @Autowired
    public DecisionService(
            DecisionRepository decisionRepository,
            MobilityRepository mobilityRepository,
            PVGeneratorService pvGeneratorService,
            AttestationGeneratorService attestationGeneratorService
    ) {
        this.decisionRepository = decisionRepository;
        this.mobilityRepository = mobilityRepository;
        this.pvGeneratorService = pvGeneratorService;
        this.attestationGeneratorService = attestationGeneratorService;
    }

    /**
     * ✅ Create or update a decision for a given mobility and generate both PV and Attestation files.
     */
    public Decision createOrUpdateDecision(DecisionRequest request) {
        Mobility mobility = mobilityRepository.findById(request.getMobilityId())
                .orElseThrow(() -> new MobilityNotFoundException("Mobility not found with ID: " + request.getMobilityId()));

        Decision decision = decisionRepository.findByMobilityId(request.getMobilityId()).orElse(new Decision());

        // ⚙️ Set decision fields
        decision.setMobility(mobility);
        decision.setMention(request.getMention());
        decision.setDecisionDate(LocalDate.now());
        decision.setVerdict(request.getVerdict());
        decision.setMadeBy(request.getMadeBy());
        decision.setMadeByRole(request.getMadeByRole());
        decision.setComment(request.getComment());

        // ✅ Génération du PV + mise à jour du chemin
        String pvPath = pvGeneratorService.generatePVForDecision(decision);
        decision.setPvPath(pvPath);

        // ✅ Génération de l'attestation + mise à jour du chemin
        String attestationPath = attestationGeneratorService.generateAttestationForDecision(decision);
        decision.setAttestationPath(attestationPath);

        // ✅ Sauvegarder la décision
        return decisionRepository.save(decision);
    }

    public Optional<Decision> getDecisionByMobility(Long mobilityId) {
        return decisionRepository.findByMobilityId(mobilityId);
    }

    public void deleteDecision(Long decisionId) {
        if (!decisionRepository.existsById(decisionId)) {
            throw new DecisionNotFoundException("Decision not found with ID: " + decisionId);
        }
        decisionRepository.deleteById(decisionId);
    }

    public List<Decision> getAllDecisions() {
        return decisionRepository.findAll();
    }
}
