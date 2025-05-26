package tech.swahell.mobiliteinternationale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.swahell.mobiliteinternationale.dto.AcademicYearRequest;
import tech.swahell.mobiliteinternationale.entity.AcademicYear;
import tech.swahell.mobiliteinternationale.entity.Mobility;
import tech.swahell.mobiliteinternationale.entity.Semester;
import tech.swahell.mobiliteinternationale.exception.AcademicYearNotFoundException;
import tech.swahell.mobiliteinternationale.exception.MobilityNotFoundException;
import tech.swahell.mobiliteinternationale.repository.AcademicYearRepository;
import tech.swahell.mobiliteinternationale.repository.MobilityRepository;

import java.util.List;

@Service
public class AcademicYearService {

    private final AcademicYearRepository academicYearRepository;
    private final MobilityRepository mobilityRepository;
    private final SemesterService semesterService;

    @Autowired
    public AcademicYearService(AcademicYearRepository academicYearRepository,
                               MobilityRepository mobilityRepository,
                               SemesterService semesterService) {
        this.academicYearRepository = academicYearRepository;
        this.mobilityRepository = mobilityRepository;
        this.semesterService = semesterService;
    }

    /**
     * ➕ Add a new academic year to a mobility
     */
    public AcademicYear addAcademicYear(AcademicYearRequest request) {
        Long mobilityId = request.getMobilityId();

        Mobility mobility = mobilityRepository.findById(mobilityId)
                .orElseThrow(() -> new MobilityNotFoundException("Mobility not found with ID: " + mobilityId));

        AcademicYear year = new AcademicYear();
        year.setYearLabel(request.getYearLabel());
        year.setMobility(mobility);

        return academicYearRepository.save(year);
    }

    /**
     * 📋 Get all academic years for a given mobility
     */
    public List<AcademicYear> getYearsByMobility(Long mobilityId) {
        return academicYearRepository.findByMobilityId(mobilityId);
    }

    /**
     * 🔍 Search academic years by year label
     */
    public List<AcademicYear> searchByLabel(String keyword) {
        return academicYearRepository.findByYearLabelContainingIgnoreCase(keyword);
    }

    /**
     * 🔍 Get one academic year by ID
     */
    public AcademicYear getById(Long id) {
        return academicYearRepository.findById(id)
                .orElseThrow(() -> new AcademicYearNotFoundException("Academic year not found with ID: " + id));
    }

    /**
     * ❌ Delete an academic year
     */
    public void deleteYear(Long id) {
        if (!academicYearRepository.existsById(id)) {
            throw new AcademicYearNotFoundException("Academic year not found with ID: " + id);
        }
        academicYearRepository.deleteById(id);
    }

    /**
     * 📊 Dynamically compute the average converted grade for this academic year
     */
    public double getAverageConvertedGradeForYear(Long academicYearId) {
        List<Semester> semesters = semesterService.getSemestersByAcademicYear(academicYearId);

        return semesters.stream()
                .mapToDouble(semester -> semesterService.getAverageConvertedGrade(semester.getId()))
                .average()
                .orElse(0.0);
    }
}
