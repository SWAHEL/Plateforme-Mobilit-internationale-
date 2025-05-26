package tech.swahell.mobiliteinternationale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.swahell.mobiliteinternationale.dto.CoordinatorRequest;
import tech.swahell.mobiliteinternationale.entity.Coordinator;
import tech.swahell.mobiliteinternationale.entity.Filiere;
import tech.swahell.mobiliteinternationale.service.CoordinatorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coordinators")
public class CoordinatorController {

    private final CoordinatorService coordinatorService;

    @Autowired
    public CoordinatorController(CoordinatorService coordinatorService) {
        this.coordinatorService = coordinatorService;
    }

    /**
     * ➕ Admin creates a new coordinator
     */
    @PostMapping("/create")
    public ResponseEntity<Coordinator> createCoordinator(@RequestBody CoordinatorRequest request) {
        Coordinator coordinator = coordinatorService.addCoordinator(
                request.getFullName(),
                request.getEmail(),
                request.getPassword(),
                Filiere.valueOf(request.getFiliere()),
                request.getCurrentUserRole()
        );
        return ResponseEntity.ok(coordinator);
    }

    /**
     * 📋 Get all coordinators
     */
    @GetMapping
    public ResponseEntity<List<Coordinator>> getAllCoordinators() {
        return ResponseEntity.ok(coordinatorService.getAll());
    }

    /**
     * 🔍 Get coordinator by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Coordinator> getById(@PathVariable Long id) {
        Coordinator coordinator = coordinatorService.getById(id);
        return ResponseEntity.ok(coordinator);
    }

    /**
     * 🔍 Get coordinators by filiere
     */
    @GetMapping("/filiere")
    public ResponseEntity<List<Coordinator>> getByFiliere(@RequestParam String filiere) {
        return ResponseEntity.ok(coordinatorService.getByFiliere(Filiere.valueOf(filiere)));
    }
}
