package tech.swahell.mobiliteinternationale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.swahell.mobiliteinternationale.entity.Coordinator;
import tech.swahell.mobiliteinternationale.entity.Filiere;

import java.util.Optional;
import java.util.List;

@Repository
public interface CoordinatorRepository extends JpaRepository<Coordinator, Long> {

    // 🔍 Find a coordinator by full name
    Optional<Coordinator> findByFullName(String fullName);

    // 🔍 Find all coordinators for a specific filiere
    List<Coordinator> findByFiliere(Filiere filiere);
}
