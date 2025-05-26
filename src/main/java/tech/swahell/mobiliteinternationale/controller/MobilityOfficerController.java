package tech.swahell.mobiliteinternationale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.swahell.mobiliteinternationale.dto.MobilityOfficerRequest;
import tech.swahell.mobiliteinternationale.entity.MobilityOfficer;
import tech.swahell.mobiliteinternationale.service.MobilityOfficerService;

import java.util.List;

@RestController
@RequestMapping("/api/mobility-officer")
public class MobilityOfficerController {

    private final MobilityOfficerService mobilityOfficerService;

    @Autowired
    public MobilityOfficerController(MobilityOfficerService mobilityOfficerService) {
        this.mobilityOfficerService = mobilityOfficerService;
    }

    /**
     * 👤 Admin creates a Mobility Officer
     */
    @PostMapping("/create")
    public ResponseEntity<MobilityOfficer> createMobilityOfficer(@RequestBody MobilityOfficerRequest request) {
        MobilityOfficer officer = mobilityOfficerService.addMobilityOfficer(
                request.getFullName(),
                request.getEmail(),
                request.getPassword(),
                request.getDepartment(),
                request.getCurrentUserRole()
        );
        return ResponseEntity.ok(officer);
    }

    /**
     * 📋 Get all mobility officers
     */
    @GetMapping
    public ResponseEntity<List<MobilityOfficer>> getAll() {
        return ResponseEntity.ok(mobilityOfficerService.getAll());
    }

    /**
     * 🔍 Get officer by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<MobilityOfficer> getById(@PathVariable Long id) {
        try {
            MobilityOfficer officer = mobilityOfficerService.getById(id);
            return ResponseEntity.ok(officer);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 🔍 Get by name
     */
    @GetMapping("/search")
    public ResponseEntity<?> getByName(@RequestParam String name) {
        return mobilityOfficerService.getByFullName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
