package tech.swahell.mobiliteinternationale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.swahell.mobiliteinternationale.dto.SemesterRequest;
import tech.swahell.mobiliteinternationale.entity.AcademicYear;
import tech.swahell.mobiliteinternationale.entity.Semester;
import tech.swahell.mobiliteinternationale.entity.SemesterType;
import tech.swahell.mobiliteinternationale.exception.AcademicYearNotFoundException;
import tech.swahell.mobiliteinternationale.exception.SemesterNotFoundException;
import tech.swahell.mobiliteinternationale.repository.AcademicYearRepository;
import tech.swahell.mobiliteinternationale.repository.SemesterRepository;

import java.util.List;

@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;
    private final AcademicYearRepository academicYearRepository;
    private final ModuleService moduleService;

    @Autowired
    public SemesterService(SemesterRepository semesterRepository,
                           AcademicYearRepository academicYearRepository,
                           ModuleService moduleService) {
        this.semesterRepository = semesterRepository;
        this.academicYearRepository = academicYearRepository;
        this.moduleService = moduleService;
    }

    public Semester addSemester(Long academicYearId, SemesterRequest request) {
        AcademicYear academicYear = academicYearRepository.findById(academicYearId)
                .orElseThrow(() -> new AcademicYearNotFoundException("Academic year not found with ID: " + academicYearId));

        Semester semester = new Semester();
        semester.setLabel(request.getLabel());
        semester.setType(request.getType() != null ? request.getType() : SemesterType.NORMAL);
        semester.setAcademicYear(academicYear);

        return semesterRepository.save(semester);
    }

    public List<Semester> getSemestersByAcademicYear(Long academicYearId) {
        return semesterRepository.findByAcademicYearId(academicYearId);
    }

    public List<Semester> getSemestersByType(Long academicYearId, SemesterType type) {
        return semesterRepository.findByAcademicYearIdAndType(academicYearId, type);
    }

    public List<Semester> searchByLabel(String keyword) {
        return semesterRepository.findByLabelContainingIgnoreCase(keyword);
    }

    public Semester getSemesterById(Long id) {
        return semesterRepository.findById(id)
                .orElseThrow(() -> new SemesterNotFoundException("Semester not found with ID: " + id));
    }

    public void deleteSemester(Long id) {
        if (!semesterRepository.existsById(id)) {
            throw new SemesterNotFoundException("Semester not found with ID: " + id);
        }
        semesterRepository.deleteById(id);
    }

    /**
     * 📊 Dynamically compute the average converted grade for a semester
     */
    public double getAverageConvertedGrade(Long semesterId) {
        return moduleService.getAverageConvertedGrade(semesterId);
    }
}
