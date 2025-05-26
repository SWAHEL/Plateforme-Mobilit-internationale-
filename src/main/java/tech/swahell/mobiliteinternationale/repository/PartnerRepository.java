package tech.swahell.mobiliteinternationale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.swahell.mobiliteinternationale.entity.Partner;

import java.util.Optional;
import java.util.List;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

    // 🔍 Find a partner by university name (exact match)
    Optional<Partner> findByUniversityName(String universityName);

    // 🔍 Find all partners in a specific country
    List<Partner> findByCountry(String country);

    // 🔍 Find partners by grading scale (exact match)
    List<Partner> findByGradingScale(String gradingScale);

    // 🔍 Optional: search by university name keyword
    List<Partner> findByUniversityNameContainingIgnoreCase(String keyword);
}
