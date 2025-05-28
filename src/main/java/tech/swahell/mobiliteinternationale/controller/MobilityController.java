package tech.swahell.mobiliteinternationale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.swahell.mobiliteinternationale.dto.MobilityOverviewDTO;
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

    // ➕ Create mobility
    @PreAuthorize("hasAnyRole('MOBILITY_OFFICER','SCHOOL_ADMIN', 'SYSTEM_ADMIN')")
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

    // 📋 Get all mobilities
    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN','MOBILITY_OFFICER', 'SYSTEM_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Mobility>> getAllMobilities() {
        return ResponseEntity.ok(mobilityService.getAllMobilities());
    }

    // 🔍 Get mobility by ID
    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN','MOBILITY_OFFICER', 'SYSTEM_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Mobility> getMobilityById(@PathVariable Long id) {
        return mobilityService.getMobilityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔍 Get mobility by student ID
    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN','MOBILITY_OFFICER', 'SYSTEM_ADMIN')")
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Mobility>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(mobilityService.getMobilitiesByStudentId(studentId));
    }

    // 📊 Get average grade
    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN','MOBILITY_OFFICER', 'SYSTEM_ADMIN')")
    @GetMapping("/{id}/average")
    public ResponseEntity<Double> getMobilityConvertedGradeAverage(@PathVariable Long id) {
        return ResponseEntity.ok(mobilityService.getMobilityConvertedGradeAverage(id));
    }

    // 🔄 Update mobility status
    @PreAuthorize("hasAnyRole('MOBILITY_OFFICER','SCHOOL_ADMIN', 'SYSTEM_ADMIN')")
    @PutMapping("/status/{mobilityId}")
    public ResponseEntity<Mobility> updateStatus(@PathVariable Long mobilityId,
                                                 @RequestParam MobilityStatus newStatus) {
        return ResponseEntity.ok(mobilityService.updateMobilityStatus(mobilityId, newStatus));
    }

    // 🔍 Filter by status
    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN','MOBILITY_OFFICER', 'SYSTEM_ADMIN')")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Mobility>> getByStatus(@PathVariable MobilityStatus status) {
        return ResponseEntity.ok(mobilityService.getMobilitiesByStatus(status));
    }

    // ❌ Delete mobility
    @PreAuthorize("hasAnyRole('MOBILITY_OFFICER','SCHOOL_ADMIN', 'SYSTEM_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMobility(@PathVariable Long id) {
        mobilityService.deleteMobility(id);
        return ResponseEntity.noContent().build();
    }

    // 🔍 Search by partner name or ID
    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN','MOBILITY_OFFICER', 'SYSTEM_ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<List<Mobility>> searchByPartner(
            @RequestParam(required = false) String partnerName,
            @RequestParam(required = false) Long partnerId
    ) {
        return ResponseEntity.ok(mobilityService.searchByPartner(partnerName, partnerId));
    }

    // 🔍 Search by type and status
    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN','MOBILITY_OFFICER', 'SYSTEM_ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<List<Mobility>> searchByTypeAndStatus(@RequestParam MobilityType type,
                                                                @RequestParam MobilityStatus status) {
        return ResponseEntity.ok(mobilityService.searchByTypeAndStatus(type, status));
    }

    // 🔍 Search by filière
    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN','MOBILITY_OFFICER', 'SYSTEM_ADMIN')")
    @GetMapping("/filiere")
    public ResponseEntity<List<Mobility>> searchByFiliere(@RequestParam String filiere) {
        return ResponseEntity.ok(mobilityService.searchByFiliere(filiere));
    }

    // 📊 Get mobilities with decision
    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN','MOBILITY_OFFICER' , 'SYSTEM_ADMIN')")
    @GetMapping("/with-decision")
    public ResponseEntity<List<Mobility>> getWithDecision() {
        return ResponseEntity.ok(mobilityService.getMobilitiesWithDecision());
    }

    // 📄 Get full overview of a specific mobility
    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN','MOBILITY_OFFICER' , 'SYSTEM_ADMIN')")
    @GetMapping("/{id}/overview")
    public ResponseEntity<MobilityOverviewDTO> getMobilityOverviewById(@PathVariable Long id) {
        return ResponseEntity.ok(mobilityService.getMobilityOverviewById(id));
    }

    // 📊 Get all mobility overviews (dashboard/report view)
    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN','MOBILITY_OFFICER' , 'SYSTEM_ADMIN')")
    @GetMapping("/overview")
    public ResponseEntity<List<MobilityOverviewDTO>> getAllMobilityOverviews() {
        return ResponseEntity.ok(mobilityService.getAllMobilityOverviews());
    }
}
