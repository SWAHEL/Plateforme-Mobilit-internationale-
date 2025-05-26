package tech.swahell.mobiliteinternationale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.swahell.mobiliteinternationale.dto.ScolariteRequest;
import tech.swahell.mobiliteinternationale.entity.Scolarite;
import tech.swahell.mobiliteinternationale.service.ScolariteService;

import java.util.List;

@RestController
@RequestMapping("/api/scolarite")
public class ScolariteController {

    private final ScolariteService scolariteService;

    @Autowired
    public ScolariteController(ScolariteService scolariteService) {
        this.scolariteService = scolariteService;
    }

    /**
     * 👤 Admin creates a new Scolarité account
     */
    @PostMapping("/create")
    public ResponseEntity<Scolarite> createScolarite(@RequestBody ScolariteRequest request) {
        Scolarite scolarite = scolariteService.addScolarite(
                request.getFullName(),
                request.getEmail(),
                request.getPassword(),
                request.getCurrentUserRole()
        );
        return ResponseEntity.ok(scolarite);
    }

    /**
     * 📋 Get all Scolarité users
     */
    @GetMapping
    public ResponseEntity<List<Scolarite>> getAllScolarites() {
        return ResponseEntity.ok(scolariteService.getAll());
    }

    /**
     * 🔍 Get by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Scolarite> getById(@PathVariable Long id) {
        return scolariteService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
