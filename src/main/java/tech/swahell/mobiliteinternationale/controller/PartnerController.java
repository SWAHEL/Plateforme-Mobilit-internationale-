package tech.swahell.mobiliteinternationale.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.swahell.mobiliteinternationale.dto.PartnerDashboardDTO;
import tech.swahell.mobiliteinternationale.dto.PartnerRequest;
import tech.swahell.mobiliteinternationale.entity.Partner;
import tech.swahell.mobiliteinternationale.service.MobilityService;
import tech.swahell.mobiliteinternationale.service.PartnerService;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
public class PartnerController {

    private final PartnerService partnerService;
    private final MobilityService mobilityService;

    @Autowired
    public PartnerController(PartnerService partnerService, MobilityService mobilityService) {
        this.partnerService = partnerService;
        this.mobilityService = mobilityService;
    }

    /**
     * 👤 Admin creates a new partner
     */
    @PostMapping("/create")
    public ResponseEntity<Partner> createPartner(@Valid @RequestBody PartnerRequest request) {
        Partner partner = partnerService.addPartner(
                request.getFullName(),
                request.getEmail(),
                request.getPassword(),
                request.getCountry(),
                request.getUniversityName(),
                request.getGradingScale(),
                request.getCurrentUserRole()
        );
        return ResponseEntity.ok(partner);
    }

    /**
     * 📋 Get all partners
     */
    @GetMapping
    public ResponseEntity<List<Partner>> getAllPartners() {
        return ResponseEntity.ok(partnerService.getAllPartners());
    }

    /**
     * 🔍 Get partner by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Partner> getPartnerById(@PathVariable Long id) {
        return ResponseEntity.ok(partnerService.getPartnerById(id));
    }

    /**
     * 🔍 Find partners by grading scale
     */
    @GetMapping("/grading-scale")
    public ResponseEntity<List<Partner>> getByGradingScale(@RequestParam String scale) {
        return ResponseEntity.ok(partnerService.getPartnersByGradingScale(scale));
    }

    /**
     * ✏️ Update partner (admin only)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Partner> updatePartner(@PathVariable Long id,
                                                 @RequestBody PartnerRequest request) {
        Partner updated = new Partner();
        updated.setCountry(request.getCountry());
        updated.setUniversityName(request.getUniversityName());
        updated.setGradingScale(request.getGradingScale());

        return ResponseEntity.ok(partnerService.updatePartner(id, updated, request.getCurrentUserRole()));
    }

    /**
     * ❌ Delete partner (admin only)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id,
                                              @RequestParam String currentUserRole) {
        partnerService.deletePartner(id, currentUserRole);
        return ResponseEntity.noContent().build();
    }

    /**
     * 📊 Dashboard for a partner (statistics)
     */
    @GetMapping("/dashboard/{partnerId}")
    @PreAuthorize("hasAnyRole('PARTNER' , 'SYSTEM_ADMIN')")
    public ResponseEntity<PartnerDashboardDTO> getPartnerDashboard(@PathVariable Long partnerId) {
        PartnerDashboardDTO dashboard = mobilityService.getDashboardForPartner(partnerId);
        return ResponseEntity.ok(dashboard);
    }
}
