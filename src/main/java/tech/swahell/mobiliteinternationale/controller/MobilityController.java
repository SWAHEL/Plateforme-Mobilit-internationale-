package tech.swahell.mobiliteinternationale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.swahell.mobiliteinternationale.dto.MobilityRequest;
import tech.swahell.mobiliteinternationale.entity.Mobility;
import tech.swahell.mobiliteinternationale.entity.MobilityStatus;
import tech.swahell.mobiliteinternationale.entity.MobilityType;
import tech.swahell.mobiliteinternationale.service.MobilityService;

import java.util.List;

@RestController
@RequestMapping("/api/mobility")
public class MobilityController {

    private final MobilityService mobilityService;

    @Autowired
    public MobilityController(MobilityService mobilityService) {
        this.mobilityService = mobilityService;
    }

    /**
     * ➕ Create a new mobility record
     */
    @PostMapping("/create")
    public ResponseEntity<Mobility> createMobility(@RequestBody MobilityRequest request) {
        Mobility mobility = mobilityService.createMobility(
                request.getStudentId(),
                MobilityType.valueOf(request.getType().toUpperCase()),
                request.getProgram(),
                request.getStartDate(),
                request.getEndDate()
        );
        return ResponseEntity.ok(mobility);
    }

    /**
     * 📋 Get all mobility records
     */
    @GetMapping
    public ResponseEntity<List<Mobility>> getAllMobilities() {
        return ResponseEntity.ok(mobilityService.getAllMobilities());
    }

    /**
     * 🔍 Get a mobility by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Mobility> getMobilityById(@PathVariable Long id) {
        return mobilityService.getMobilityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    /**
     * 🔍 Get mobility records by student ID
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Mobility>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(mobilityService.getMobilitiesByStudentId(studentId));
    }

    /**
     * 📊 Get average converted grade for a mobility (computed dynamically)
     */
    @GetMapping("/{id}/average")
    public ResponseEntity<Double> getMobilityConvertedGradeAverage(@PathVariable Long id) {
        return ResponseEntity.ok(mobilityService.getMobilityConvertedGradeAverage(id));
    }


    /**
     * 🔄 Update status of a mobility record
     */
    @PutMapping("/status/{mobilityId}")
    public ResponseEntity<Mobility> updateStatus(@PathVariable Long mobilityId,
                                                 @RequestParam MobilityStatus newStatus) {
        return ResponseEntity.ok(mobilityService.updateMobilityStatus(mobilityId, newStatus));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Mobility>> getByStatus(@PathVariable MobilityStatus status) {
        return ResponseEntity.ok(mobilityService.getMobilitiesByStatus(status));
    }


    /**
     * ❌ Delete a mobility record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMobility(@PathVariable Long id) {
        mobilityService.deleteMobility(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 🔍 Search mobilities by partner name or ID
     */
    @GetMapping("/search")
    public ResponseEntity<List<Mobility>> searchByPartner(
            @RequestParam(required = false) String partnerName,
            @RequestParam(required = false) Long partnerId
    ) {
        return ResponseEntity.ok(mobilityService.searchByPartner(partnerName, partnerId));
    }

    /**
     * 🔍 Search by type and status
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Mobility>> searchByTypeAndStatus(@RequestParam MobilityType type,
                                                                @RequestParam MobilityStatus status) {
        return ResponseEntity.ok(mobilityService.searchByTypeAndStatus(type, status));
    }

    /**
     * 🔍 Filter mobilities by student filière
     */
    @GetMapping("/filiere")
    public ResponseEntity<List<Mobility>> searchByFiliere(@RequestParam String filiere) {
        return ResponseEntity.ok(mobilityService.searchByFiliere(filiere));
    }

    /**
     * 📊 Get all mobilities that reached decision phase
     */
    @GetMapping("/with-decision")
    public ResponseEntity<List<Mobility>> getWithDecision() {
        return ResponseEntity.ok(mobilityService.getMobilitiesWithDecision());
    }


}
