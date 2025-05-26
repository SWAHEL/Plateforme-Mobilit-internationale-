package tech.swahell.mobiliteinternationale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.swahell.mobiliteinternationale.entity.Role;
import tech.swahell.mobiliteinternationale.entity.Scolarite;
import tech.swahell.mobiliteinternationale.exception.UnauthorizedAccessException;
import tech.swahell.mobiliteinternationale.repository.ScolariteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ScolariteService {

    private final ScolariteRepository scolariteRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ScolariteService(ScolariteRepository scolariteRepository, PasswordEncoder passwordEncoder) {
        this.scolariteRepository = scolariteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 👤 Add a new Scolarité account (admin-only)
     */
    public Scolarite addScolarite(String fullName, String email, String rawPassword, String currentUserRole) {
        if (!"SYSTEM_ADMIN".equals(currentUserRole)) {
            throw new UnauthorizedAccessException("Only admins are allowed to create Scolarité users.");
        }

        Scolarite scolarite = new Scolarite();
        scolarite.setFullName(fullName);
        scolarite.setEmail(email);
        scolarite.setPassword(passwordEncoder.encode(rawPassword));
        scolarite.setRole(Role.SCHOOL_ADMIN);

        return scolariteRepository.save(scolarite);
    }

    /**
     * 🔍 Get a Scolarité user by ID
     */
    public Optional<Scolarite> getById(Long id) {
        return scolariteRepository.findById(id);
    }

    /**
     * 📋 Get all Scolarité users
     */
    public List<Scolarite> getAll() {
        return scolariteRepository.findAll();
    }

    /**
     * 🔍 Get a Scolarité user by full name
     */
    public Optional<Scolarite> getByFullName(String fullName) {
        return scolariteRepository.findByFullName(fullName);
    }
}
