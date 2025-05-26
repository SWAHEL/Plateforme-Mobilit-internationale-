package tech.swahell.mobiliteinternationale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.swahell.mobiliteinternationale.entity.Semester;
import tech.swahell.mobiliteinternationale.entity.SemesterType;

import java.util.List;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {

    /**
     * 🔍 Get all semesters for a given academic year
     */
    List<Semester> findByAcademicYearId(Long academicYearId);

    /**
     * 🔍 Get semesters of a specific type (e.g., NORMAL, PFE) in a given academic year
     */
    List<Semester> findByAcademicYearIdAndType(Long academicYearId, SemesterType type);

    /**
     * 🔍 Search by label (e.g., "S1", "S2", "Spring 2024")
     */
    List<Semester> findByLabelContainingIgnoreCase(String keyword);
}
