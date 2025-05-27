package tech.swahell.mobiliteinternationale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.swahell.mobiliteinternationale.entity.*;
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

    public List<Mobility> getAllMobilities() {
        return mobilityRepository.findAll();
    }

    public List<Mobility> getMobilitiesByStudentId(Long studentId) {
        return mobilityRepository.findByStudentId(studentId);
    }

    public Mobility updateMobilityStatus(Long mobilityId, MobilityStatus newStatus) {
        Mobility mobility = mobilityRepository.findById(mobilityId)
                .orElseThrow(() -> new MobilityNotFoundException("Mobilité non trouvée avec l'ID : " + mobilityId));

        MobilityStatus currentStatus = mobility.getStatus();

        if (!isValidTransition(currentStatus, newStatus)) {
            throw new IllegalStateException("Transition invalide : " + currentStatus + " → " + newStatus);
        }

        mobility.setStatus(newStatus);
        return mobilityRepository.save(mobility);
    }

    private boolean isValidTransition(MobilityStatus current, MobilityStatus target) {
        return switch (current) {
            case PREPARATION -> target == MobilityStatus.PENDING_DOCS;
            case PENDING_DOCS -> target == MobilityStatus.VERIFIED;
            case VERIFIED -> target == MobilityStatus.COMMISSION;
            case COMMISSION -> target == MobilityStatus.VALIDATED || target == MobilityStatus.REJECTED;
            case VALIDATED, REJECTED -> false;
        };
    }

    public void deleteMobility(Long id) {
        if (!mobilityRepository.existsById(id)) {
            throw new MobilityNotFoundException("Impossible de supprimer : mobilité non trouvée avec l'ID : " + id);
        }
        mobilityRepository.deleteById(id);
    }

    public Optional<Mobility> getMobilityById(Long id) {
        return mobilityRepository.findById(id);
    }

    public List<Mobility> searchByPartner(String partnerName, Long partnerId) {
        if (partnerId != null) {
            return mobilityRepository.findByStudent_Partner_Id(partnerId);
        } else if (partnerName != null && !partnerName.isEmpty()) {
            return mobilityRepository.findByStudent_Partner_UniversityNameContainingIgnoreCase(partnerName);
        } else {
            throw new IllegalArgumentException("Vous devez fournir un partnerName ou un partnerId.");
        }
    }

    public double getMobilityConvertedGradeAverage(Long mobilityId) {
        return academicYearService.getYearsByMobility(mobilityId).stream()
                .mapToDouble(year -> academicYearService.getAverageConvertedGradeForYear(year.getId()))
                .average()
                .orElse(0.0);
    }

    public List<Mobility> searchByTypeAndStatus(MobilityType type, MobilityStatus status) {
        return mobilityRepository.findByTypeAndStatus(type, status);
    }

    public List<Mobility> searchByFiliere(String filiere) {
        return mobilityRepository.findByStudent_Filiere(filiere);
    }

    public List<Mobility> getMobilitiesWithDecision() {
        return mobilityRepository.findByDecisionIsNotNull();
    }

    public List<Mobility> getMobilitiesByStatus(MobilityStatus status) {
        return mobilityRepository.findByStatus(status);
    }
}
