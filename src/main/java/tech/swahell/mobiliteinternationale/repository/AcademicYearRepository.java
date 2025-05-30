package tech.swahell.mobiliteinternationale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.swahell.mobiliteinternationale.entity.AcademicYear;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {

    /**
     * 🔍 Get all academic years for a given mobility
     */
    List<AcademicYear> findByMobilityId(Long mobilityId);

    /**
     * 🔍 Search academic years by year label (e.g., "2023-2024")
     */
    List<AcademicYear> findByYearLabelContainingIgnoreCase(String keyword);

    /**
     * 🔍 Find one academic year by label and mobility
     */
    Optional<AcademicYear> findByYearLabelAndMobilityId(String yearLabel, Long mobilityId);
}
