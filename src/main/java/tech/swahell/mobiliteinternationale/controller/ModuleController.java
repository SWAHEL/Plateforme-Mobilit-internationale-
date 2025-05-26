package tech.swahell.mobiliteinternationale.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.swahell.mobiliteinternationale.dto.ModuleRequest;
import tech.swahell.mobiliteinternationale.entity.Module;
import tech.swahell.mobiliteinternationale.service.ModuleService;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    private final ModuleService moduleService;

    @Autowired
    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    /**
     * ➕ Add a module to a specific semester
     */
    @PostMapping("/semester/{semesterId}")
    public ResponseEntity<Module> addModuleToSemester(
            @PathVariable Long semesterId,
            @Valid @RequestBody ModuleRequest request) {
        Module savedModule = moduleService.addModule(semesterId, request);
        return ResponseEntity.ok(savedModule);
    }

    /**
     * 📋 Get all modules associated with a specific semester
     */
    @GetMapping("/semester/{semesterId}")
    public ResponseEntity<List<Module>> getModulesBySemester(@PathVariable Long semesterId) {
        return ResponseEntity.ok(moduleService.getModulesBySemester(semesterId));
    }

    /**
     * 🔍 Search for modules by keyword in name
     */
    @GetMapping("/search")
    public ResponseEntity<List<Module>> searchModules(@RequestParam String keyword) {
        return ResponseEntity.ok(moduleService.searchModules(keyword));
    }

    /**
     * ❌ Delete a module by its ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 📊 Get the average converted grade of all modules in a semester
     */
    @GetMapping("/semester/{semesterId}/average")
    public ResponseEntity<Double> getAverageConvertedGrade(@PathVariable Long semesterId) {
        return ResponseEntity.ok(moduleService.getAverageConvertedGrade(semesterId));
    }

    /**
     * 🎓 Get all modules marked as PFE (final project) in a semester
     */
    @GetMapping("/semester/{semesterId}/pfe")
    public ResponseEntity<List<Module>> getPfeModules(@PathVariable Long semesterId) {
        return ResponseEntity.ok(moduleService.getPfeModules(semesterId));
    }

    /**
     * 📘 Get all non-PFE (regular) modules in a semester
     */
    @GetMapping("/semester/{semesterId}/normal")
    public ResponseEntity<List<Module>> getNormalModules(@PathVariable Long semesterId) {
        return ResponseEntity.ok(moduleService.getNormalModules(semesterId));
    }
}
