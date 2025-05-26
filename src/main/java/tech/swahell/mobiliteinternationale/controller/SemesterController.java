package tech.swahell.mobiliteinternationale.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.swahell.mobiliteinternationale.dto.SemesterRequest;
import tech.swahell.mobiliteinternationale.entity.Semester;
import tech.swahell.mobiliteinternationale.entity.SemesterType;
import tech.swahell.mobiliteinternationale.service.SemesterService;

import java.util.List;

@RestController
@RequestMapping("/api/semesters")
public class SemesterController {

    private final SemesterService semesterService;

    @Autowired
    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    /**
     * ✅ Create a semester using request body (label, type, academicYearId)
     */
    @PostMapping("/create")
    public ResponseEntity<Semester> createSemester(@Valid @RequestBody SemesterRequest request) {
        Semester semester = semesterService.addSemester(request.getAcademicYearId(), request);
        return ResponseEntity.ok(semester);
    }

    /**
     * 📋 Get all semesters for a specific academic year
     */
    @GetMapping("/academic-year/{academicYearId}")
    public ResponseEntity<List<Semester>> getSemestersByAcademicYear(@PathVariable Long academicYearId) {
        return ResponseEntity.ok(semesterService.getSemestersByAcademicYear(academicYearId));
    }

    /**
     * 🔍 Get semesters of a specific type (NORMAL or PFE) for a given academic year
     */
    @GetMapping("/academic-year/{academicYearId}/type")
    public ResponseEntity<List<Semester>> getSemestersByType(@PathVariable Long academicYearId,
                                                             @RequestParam SemesterType type) {
        return ResponseEntity.ok(semesterService.getSemestersByType(academicYearId, type));
    }

    /**
     * 🔍 Search semesters by label (e.g., S1, PFE)
     */
    @GetMapping("/search")
    public ResponseEntity<List<Semester>> searchByLabel(@RequestParam String keyword) {
        return ResponseEntity.ok(semesterService.searchByLabel(keyword));
    }

    /**
     * 🔍 Get a semester by its ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Semester> getSemesterById(@PathVariable Long id) {
        return ResponseEntity.ok(semesterService.getSemesterById(id));
    }

    /**
     * 📊 Get average converted grade for a semester (dynamic)
     */
    @GetMapping("/{id}/average")
    public ResponseEntity<Double> getSemesterAverage(@PathVariable Long id) {
        double average = semesterService.getAverageConvertedGrade(id);
        return ResponseEntity.ok(average);
    }

    /**
     * ❌ Delete a semester by its ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSemester(@PathVariable Long id) {
        semesterService.deleteSemester(id);
        return ResponseEntity.noContent().build();
    }
}
