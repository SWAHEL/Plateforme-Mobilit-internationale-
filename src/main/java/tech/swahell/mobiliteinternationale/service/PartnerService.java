package tech.swahell.mobiliteinternationale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.swahell.mobiliteinternationale.entity.Partner;
import tech.swahell.mobiliteinternationale.entity.Role;
import tech.swahell.mobiliteinternationale.exception.PartnerNotFoundException;
import tech.swahell.mobiliteinternationale.exception.UnauthorizedAccessException;
import tech.swahell.mobiliteinternationale.repository.PartnerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository, PasswordEncoder passwordEncoder) {
        this.partnerRepository = partnerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Add a new partner (restricted to admins)
     */
    public Partner addPartner(String fullName, String email, String rawPassword, String country,
                              String universityName, String gradingScale, String currentUserRole) {
        if (!"SYSTEM_ADMIN".equals(currentUserRole)) {
            throw new UnauthorizedAccessException("Only admins are allowed to add partners.");
        }

        Partner partner = new Partner();
        partner.setFullName(fullName);
        partner.setEmail(email);
        partner.setPassword(passwordEncoder.encode(rawPassword));
        partner.setCountry(country);
        partner.setUniversityName(universityName);
        partner.setGradingScale(gradingScale);
        partner.setRole(Role.PARTNER);

        return partnerRepository.save(partner);
    }

    /**
     * Get all partners
     */
    public List<Partner> getAllPartners() {
        return partnerRepository.findAll();
    }

    /**
     * Find partner by ID — throws if not found
     */
    public Partner getPartnerById(Long id) {
        return partnerRepository.findById(id)
                .orElseThrow(() -> new PartnerNotFoundException("Partner not found with id: " + id));
    }

    /**
     * Find partner by ID — returns Optional
     */
    public Optional<Partner> findOptionalById(Long id) {
        return partnerRepository.findById(id);
    }

    /**
     * Find by grading scale
     */
    public List<Partner> getPartnersByGradingScale(String gradingScale) {
        return partnerRepository.findByGradingScale(gradingScale);
    }

    /**
     * Delete a partner (restricted to admins)
     */
    public void deletePartner(Long id, String currentUserRole) {
        if (!"SYSTEM_ADMIN".equals(currentUserRole)) {
            throw new UnauthorizedAccessException("Only admins are allowed to delete partners.");
        }
        if (!partnerRepository.existsById(id)) {
            throw new PartnerNotFoundException("Cannot delete: partner not found with id: " + id);
        }
        partnerRepository.deleteById(id);
    }

    /**
     * Update partner information (restricted to admins)
     */
    public Partner updatePartner(Long id, Partner updatedPartner, String currentUserRole) {
        if (!"SYSTEM_ADMIN".equals(currentUserRole)) {
            throw new UnauthorizedAccessException("Only admins are allowed to update partners.");
        }

        return partnerRepository.findById(id).map(partner -> {
            partner.setCountry(updatedPartner.getCountry());
            partner.setUniversityName(updatedPartner.getUniversityName());
            partner.setGradingScale(updatedPartner.getGradingScale());
            return partnerRepository.save(partner);
        }).orElseThrow(() -> new PartnerNotFoundException("Partner not found with id: " + id));
    }
}
