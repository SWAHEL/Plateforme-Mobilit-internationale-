package tech.swahell.mobiliteinternationale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.swahell.mobiliteinternationale.dto.ModuleRequest;
import tech.swahell.mobiliteinternationale.entity.Module;
import tech.swahell.mobiliteinternationale.entity.Semester;
import tech.swahell.mobiliteinternationale.exception.ModuleNotFoundException;
import tech.swahell.mobiliteinternationale.exception.SemesterNotFoundException;
import tech.swahell.mobiliteinternationale.repository.ModuleRepository;
import tech.swahell.mobiliteinternationale.repository.SemesterRepository;
import tech.swahell.mobiliteinternationale.utils.GradeConverter;

import java.util.List;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final SemesterRepository semesterRepository;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository, SemesterRepository semesterRepository) {
        this.moduleRepository = moduleRepository;
        this.semesterRepository = semesterRepository;
    }

    /**
     * ➕ Add a module to a semester (with automatic grade conversion)
     */
    public Module addModule(Long semesterId, ModuleRequest request) {
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new SemesterNotFoundException("Semester not found with ID: " + semesterId));

        // 🔁 Get partner country from the chain: semester → academicYear → mobility → student → partner
        String country = semester.getAcademicYear()
                .getMobility()
                .getStudent()
                .getPartner()
                .getCountry();

        // 🔢 Convert the original grade to ENSIAS scale
        double convertedGrade = GradeConverter.convert(request.getOriginalGrade(), country);

        Module module = new Module(
                request.getName(),
                request.getOriginalGrade(),
                convertedGrade,
                request.getEcts(),
                request.isPfe(),
                semester
        );

        return moduleRepository.save(module);
    }

    /**
     * 📋 Get all modules for a given semester
     */
    public List<Module> getModulesBySemester(Long semesterId) {
        return moduleRepository.findBySemesterId(semesterId);
    }

    /**
     * ❌ Delete a module by ID
     */
    public void deleteModule(Long id) {
        if (!moduleRepository.existsById(id)) {
            throw new ModuleNotFoundException("Module not found with ID: " + id);
        }
        moduleRepository.deleteById(id);
    }

    /**
     * 🔍 Search modules by keyword
     */
    public List<Module> searchModules(String keyword) {
        return moduleRepository.findByNameContainingIgnoreCase(keyword);
    }

    /**
     * 📊 Calculate average converted grade for a semester
     */
    public double getAverageConvertedGrade(Long semesterId) {
        List<Module> modules = moduleRepository.findBySemesterId(semesterId);
        return modules.stream()
                .mapToDouble(Module::getConvertedGrade)
                .average()
                .orElse(0.0);
    }

    /**
     * 🔍 Get PFE modules only
     */
    public List<Module> getPfeModules(Long semesterId) {
        return moduleRepository.findBySemesterIdAndIsPfeTrue(semesterId);
    }

    /**
     * 🔍 Get normal (non-PFE) modules
     */
    public List<Module> getNormalModules(Long semesterId) {
        return moduleRepository.findBySemesterIdAndIsPfeFalse(semesterId);
    }
}
