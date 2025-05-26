package tech.swahell.mobiliteinternationale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.swahell.mobiliteinternationale.entity.Scolarite;

import java.util.Optional;
import java.util.List;

@Repository
public interface ScolariteRepository extends JpaRepository<Scolarite, Long> {

    // 🔍 Find a scolarité (staff) by full name
    Optional<Scolarite> findByFullName(String fullName);

    Optional<Scolarite> findByEmail(String email);

    // 🔍 Optional: get all scolarité users by role if you assign sub-roles later
    List<Scolarite> findByRole(tech.swahell.mobiliteinternationale.entity.Role role);
}
