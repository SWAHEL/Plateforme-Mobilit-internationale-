package tech.swahell.mobiliteinternationale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Autowired
    public DecisionService(DecisionRepository decisionRepository, MobilityRepository mobilityRepository) {
        this.decisionRepository = decisionRepository;
        this.mobilityRepository = mobilityRepository;
    }

    /**
     * ✅ Create or update a decision for a given mobility.
     */
    public Decision createOrUpdateDecision(Long mobilityId, String mention, String pvPath) {
        Mobility mobility = mobilityRepository.findById(mobilityId)
                .orElseThrow(() -> new MobilityNotFoundException("Mobility not found with ID: " + mobilityId));

        Decision decision = decisionRepository.findByMobilityId(mobilityId).orElse(new Decision());
        decision.setMobility(mobility);
        decision.setMention(mention);
        decision.setPvPath(pvPath);
        decision.setDecisionDate(LocalDate.now());

        return decisionRepository.save(decision);
    }

    /**
     * ✅ Get the decision for a specific mobility ID.
     */
    public Optional<Decision> getDecisionByMobility(Long mobilityId) {
        return decisionRepository.findByMobilityId(mobilityId);
    }

    /**
     * ❌ Delete a decision by ID.
     */
    public void deleteDecision(Long decisionId) {
        if (!decisionRepository.existsById(decisionId)) {
            throw new DecisionNotFoundException("Decision not found with ID: " + decisionId);
        }
        decisionRepository.deleteById(decisionId);
    }

    /**
     * 📋 Return all decisions.
     */
    public List<Decision> getAllDecisions() {
        return decisionRepository.findAll();
    }
}
