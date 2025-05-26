package tech.swahell.mobiliteinternationale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.swahell.mobiliteinternationale.entity.Coordinator;
import tech.swahell.mobiliteinternationale.entity.Filiere;
import tech.swahell.mobiliteinternationale.entity.Role;
import tech.swahell.mobiliteinternationale.exception.CoordinatorNotFoundException;
import tech.swahell.mobiliteinternationale.exception.UnauthorizedAccessException;
import tech.swahell.mobiliteinternationale.repository.CoordinatorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CoordinatorService {

    private final CoordinatorRepository coordinatorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CoordinatorService(CoordinatorRepository coordinatorRepository, PasswordEncoder passwordEncoder) {
        this.coordinatorRepository = coordinatorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 👤 Add a new coordinator (admin-only)
     */
    public Coordinator addCoordinator(String fullName, String email, String rawPassword, Filiere filiere, String currentUserRole) {
        if (!"SYSTEM_ADMIN".equals(currentUserRole)) {
            throw new UnauthorizedAccessException("Only SCHOOL_ADMIN can add coordinators.");
        }

        Coordinator coordinator = new Coordinator();
        coordinator.setFullName(fullName);
        coordinator.setEmail(email);
        coordinator.setPassword(passwordEncoder.encode(rawPassword));
        coordinator.setFiliere(filiere);
        coordinator.setRole(Role.COORDINATOR);
        return coordinatorRepository.save(coordinator);
    }

    /**
     * 🔍 Get coordinator by ID
     */
    public Coordinator getById(Long id) {
        return coordinatorRepository.findById(id)
                .orElseThrow(() -> new CoordinatorNotFoundException("Coordinator not found with ID: " + id));
    }

    /**
     * 📜 Get all coordinators
     */
    public List<Coordinator> getAll() {
        return coordinatorRepository.findAll();
    }

    /**
     * 🔍 Get all coordinators for a specific filière
     */
    public List<Coordinator> getByFiliere(Filiere filiere) {
        return coordinatorRepository.findByFiliere(filiere);
    }

    /**
     * ❌ Delete a coordinator by ID (admin-only)
     */
    public void deleteCoordinator(Long id, String currentUserRole) {
        if (!"SYSTEM_ADMIN".equals(currentUserRole)) {
            throw new UnauthorizedAccessException("Only SCHOOL_ADMIN can delete coordinators.");
        }

        if (!coordinatorRepository.existsById(id)) {
            throw new CoordinatorNotFoundException("Coordinator not found with ID: " + id);
        }

        coordinatorRepository.deleteById(id);
    }
}
