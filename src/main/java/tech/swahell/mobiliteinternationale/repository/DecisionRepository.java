package tech.swahell.mobiliteinternationale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.swahell.mobiliteinternationale.entity.Decision;
import tech.swahell.mobiliteinternationale.entity.Mobility;

import java.util.Optional;

@Repository
public interface DecisionRepository extends JpaRepository<Decision, Long> {

    // 🔍 Get the decision associated with a Mobility object
    Optional<Decision> findByMobility(Mobility mobility);

    // 🔍 Get the decision associated with a Mobility ID
    Optional<Decision> findByMobilityId(Long mobilityId);
}
