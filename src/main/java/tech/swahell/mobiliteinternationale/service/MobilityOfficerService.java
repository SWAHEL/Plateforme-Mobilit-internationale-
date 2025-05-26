package tech.swahell.mobiliteinternationale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.swahell.mobiliteinternationale.entity.MobilityOfficer;
import tech.swahell.mobiliteinternationale.entity.Role;
import tech.swahell.mobiliteinternationale.exception.MobilityOfficerNotFoundException;
import tech.swahell.mobiliteinternationale.exception.UnauthorizedAccessException;
import tech.swahell.mobiliteinternationale.repository.MobilityOfficerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MobilityOfficerService {

    private final MobilityOfficerRepository mobilityOfficerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MobilityOfficerService(MobilityOfficerRepository mobilityOfficerRepository,
                                  PasswordEncoder passwordEncoder) {
        this.mobilityOfficerRepository = mobilityOfficerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 👤 Only SCHOOL_ADMIN can create a mobility officer.
     */
    public MobilityOfficer addMobilityOfficer(String fullName, String email, String rawPassword,
                                              String department, String currentUserRole) {
        if (!"SYSTEM_ADMIN".equals(currentUserRole)) {
            throw new UnauthorizedAccessException("Only admins can create a Mobility Officer account.");
        }

        MobilityOfficer officer = new MobilityOfficer();
        officer.setFullName(fullName);
        officer.setEmail(email);
        officer.setPassword(passwordEncoder.encode(rawPassword));
        officer.setDepartment(department);
        officer.setRole(Role.MOBILITY_OFFICER);

        return mobilityOfficerRepository.save(officer);
    }

    /**
     * 🔍 Retrieve a mobility officer by ID or throw a custom exception.
     */
    public MobilityOfficer getById(Long id) {
        return mobilityOfficerRepository.findById(id)
                .orElseThrow(() -> new MobilityOfficerNotFoundException("Mobility Officer not found with ID: " + id));
    }

    /**
     * 📋 Retrieve all mobility officers.
     */
    public List<MobilityOfficer> getAll() {
        return mobilityOfficerRepository.findAll();
    }

    /**
     * 🔍 Retrieve a mobility officer by full name (Optional).
     */
    public Optional<MobilityOfficer> getByFullName(String fullName) {
        return mobilityOfficerRepository.findByFullName(fullName);
    }
}
