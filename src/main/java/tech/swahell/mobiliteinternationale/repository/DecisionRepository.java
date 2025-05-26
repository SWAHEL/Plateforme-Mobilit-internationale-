package tech.swahell.mobiliteinternationale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.swahell.mobiliteinternationale.entity.Decision;
import tech.swahell.mobiliteinternationale.entity.Mobility;

import java.util.Optional;

@Repository
public interface DecisionRepository extends JpaRepository<Decision, Long> {

    // 🔍 Obtenir la décision liée à une instance de Mobility
    Optional<Decision> findByMobility(Mobility mobility);

    // 🔍 Obtenir la décision liée à un ID de mobilité
    Optional<Decision> findByMobilityId(Long mobilityId);
}
