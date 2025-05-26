package tech.swahell.mobiliteinternationale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.swahell.mobiliteinternationale.entity.MobilityOfficer;

import java.util.Optional;

@Repository
public interface MobilityOfficerRepository extends JpaRepository<MobilityOfficer, Long> {

    // 🔍 Find the mobility officer by full name (optional, since there's only one)
    Optional<MobilityOfficer> findByFullName(String fullName);

}
