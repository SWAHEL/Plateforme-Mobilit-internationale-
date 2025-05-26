package tech.swahell.mobiliteinternationale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.swahell.mobiliteinternationale.entity.Mobility;
import tech.swahell.mobiliteinternationale.entity.Student;
import tech.swahell.mobiliteinternationale.entity.MobilityStatus;
import tech.swahell.mobiliteinternationale.entity.MobilityType;
import tech.swahell.mobiliteinternationale.exception.MobilityNotFoundException;
import tech.swahell.mobiliteinternationale.exception.StudentNotFoundException;
import tech.swahell.mobiliteinternationale.repository.MobilityRepository;
import tech.swahell.mobiliteinternationale.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MobilityService {

    private final MobilityRepository mobilityRepository;
    private final StudentRepository studentRepository;
    private final AcademicYearService academicYearService;

    @Autowired
    public MobilityService(MobilityRepository mobilityRepository,
                           StudentRepository studentRepository,
                           AcademicYearService academicYearService) {
        this.mobilityRepository = mobilityRepository;
        this.studentRepository = studentRepository;
        this.academicYearService = academicYearService;
    }

    /**
     * ➕ Create a new mobility for a student
     */
    public Mobility createMobility(Long studentId, MobilityType type, String program, LocalDate startDate, LocalDate endDate) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Étudiant non trouvé avec l'ID : " + studentId));

        Mobility mobility = new Mobility();
        mobility.setStudent(student);
        mobility.setType(type);
        mobility.setProgram(program);
        mobility.setStartDate(startDate);
        mobility.setEndDate(endDate);
        mobility.setStatus(MobilityStatus.PREPARATION);

        return mobilityRepository.save(mobility);
    }

    /**
     * 📋 Retrieve all mobility records
     */
    public List<Mobility> getAllMobilities() {
        return mobilityRepository.findAll();
    }

    /**
     * 🔍 Retrieve all mobilities for a given student ID
     */
    public List<Mobility> getMobilitiesByStudentId(Long studentId) {
        return mobilityRepository.findByStudentId(studentId);
    }

    /**
     * 🔄 Update the status of a mobility
     */
    public Mobility updateMobilityStatus(Long mobilityId, MobilityStatus newStatus) {
        return mobilityRepository.findById(mobilityId)
                .map(mobility -> {
                    mobility.setStatus(newStatus);
                    return mobilityRepository.save(mobility);
                })
                .orElseThrow(() -> new MobilityNotFoundException("Mobilité non trouvée avec l'ID : " + mobilityId));
    }

    /**
     * ❌ Delete a mobility by ID
     */
    public void deleteMobility(Long id) {
        if (!mobilityRepository.existsById(id)) {
            throw new MobilityNotFoundException("Impossible de supprimer : mobilité non trouvée avec l'ID : " + id);
        }
        mobilityRepository.deleteById(id);
    }

    /**
     * 🔍 Find a mobility by its ID
     */
    public Optional<Mobility> getMobilityById(Long id) {
        return mobilityRepository.findById(id);
    }

    /**
     * 🔍 Search mobility records by partner name or ID
     */
    public List<Mobility> searchByPartner(String partnerName, Long partnerId) {
        if (partnerId != null) {
            return mobilityRepository.findByStudent_Partner_Id(partnerId);
        } else if (partnerName != null && !partnerName.isEmpty()) {
            return mobilityRepository.findByStudent_Partner_UniversityNameContainingIgnoreCase(partnerName);
        } else {
            throw new IllegalArgumentException("Vous devez fournir un partnerName ou un partnerId.");
        }
    }

    /**
     * 📊 Calculate the average converted grade for a mobility
     */
    public double getMobilityConvertedGradeAverage(Long mobilityId) {
        return academicYearService.getYearsByMobility(mobilityId).stream()
                .mapToDouble(year -> academicYearService.getAverageConvertedGradeForYear(year.getId()))
                .average()
                .orElse(0.0);
    }
}
