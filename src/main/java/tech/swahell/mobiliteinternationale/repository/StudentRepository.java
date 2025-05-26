package tech.swahell.mobiliteinternationale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.swahell.mobiliteinternationale.entity.Student;
import tech.swahell.mobiliteinternationale.entity.Filiere;
import tech.swahell.mobiliteinternationale.entity.Partner;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // 🔍 Find a student by full name (exact match)
    Optional<Student> findByFullName(String fullName);

    // 🔍 Find all students in a given filiere
    List<Student> findByFiliere(Filiere filiere);

    // 🔍 Search students with name containing a keyword (case-insensitive)
    List<Student> findByFullNameContainingIgnoreCase(String keyword);

    // 🔍 Find students by their assigned partner
    List<Student> findByPartner(Partner partner);
}
