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

    Optional<Mobility> findByStudent(Student student);

    List<Mobility> findByStudentId(Long studentId);

    List<Mobility> findByType(MobilityType type);

    List<Mobility> findByStatus(MobilityStatus status);

    List<Mobility> findByTypeAndStatus(MobilityType type, MobilityStatus status);

    List<Mobility> findByStudent_Filiere(String filiere);

    List<Mobility> findByDecisionIsNotNull();

    List<Mobility> findByStudent_Partner_UniversityNameContainingIgnoreCase(String universityName);

    List<Mobility> findByStudent_Partner_Id(Long partnerId); // ✅ utile pour getDashboardForPartner()
}
