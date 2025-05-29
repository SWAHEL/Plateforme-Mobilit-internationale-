package tech.swahell.mobiliteinternationale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.swahell.mobiliteinternationale.dto.DecisionRequest;
import tech.swahell.mobiliteinternationale.entity.Decision;
import tech.swahell.mobiliteinternationale.service.DecisionService;

import java.util.List;

@RestController
@RequestMapping("/api/decisions")
public class DecisionController {

    private final DecisionService decisionService;

    @Autowired
    public DecisionController(DecisionService decisionService) {
        this.decisionService = decisionService;
    }

    /**
     * ➕ Save (create or update) a decision for a mobility.
     */
    @PreAuthorize("hasAnyRole('COORDINATOR','DAR','DAP','MOBILITY_OFFICER','SCHOOL_ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<Decision> saveDecision(@RequestBody DecisionRequest request) {
        Decision decision = decisionService.createOrUpdateDecision(request);
        return ResponseEntity.ok(decision);
    }

    /**
     * 🔍 Get decision by mobility ID.
     */
    @PreAuthorize("hasAnyRole('COORDINATOR','DAR','DAP','MOBILITY_OFFICER','SCHOOL_ADMIN')")
    @GetMapping("/mobility/{mobilityId}")
    public ResponseEntity<Decision> getByMobility(@PathVariable Long mobilityId) {
        return decisionService.getDecisionByMobility(mobilityId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 📋 Get all decisions.
     */
    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN','MOBILITY_OFFICER')")
    @GetMapping
    public ResponseEntity<List<Decision>> getAll() {
        return ResponseEntity.ok(decisionService.getAllDecisions());
    }

    /**
     * ❌ Delete a decision by ID.
     */
    @PreAuthorize("hasRole('SCHOOL_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        decisionService.deleteDecision(id);
        return ResponseEntity.noContent().build();
    }
}
