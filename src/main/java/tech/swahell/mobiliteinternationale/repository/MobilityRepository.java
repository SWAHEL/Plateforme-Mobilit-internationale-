package tech.swahell.mobiliteinternationale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.swahell.mobiliteinternationale.entity.Mobility;
import tech.swahell.mobiliteinternationale.entity.Student;
import tech.swahell.mobiliteinternationale.entity.MobilityType;
import tech.swahell.mobiliteinternationale.entity.MobilityStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface MobilityRepository extends JpaRepository<Mobility, Long> {

    // 🔍 Find a mobility by student entity (1-to-1 relationship)
    Optional<Mobility> findByStudent(Student student);

    // 🔍 Find all mobilities by student ID
    List<Mobility> findByStudentId(Long studentId);

    // 🔍 Find all mobilities of a given type (EXCHANGE or DOUBLE_DIPLOMA)
    List<Mobility> findByType(MobilityType type);

    // 🔍 Find all mobilities with a certain status (PENDING_DOCS, COMMISSION, etc.)
    List<Mobility> findByStatus(MobilityStatus status);

    // ✅ New: Search mobilities by partner university name (partial match, case-insensitive)
    List<Mobility> findByStudent_Partner_UniversityNameContainingIgnoreCase(String universityName);

    // ✅ New: Search mobilities by partner ID
    List<Mobility> findByStudent_Partner_Id(Long partnerId);
}
