package tech.swahell.mobiliteinternationale.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.swahell.mobiliteinternationale.dto.AcademicYearRequest;
import tech.swahell.mobiliteinternationale.entity.AcademicYear;
import tech.swahell.mobiliteinternationale.service.AcademicYearService;

import java.util.List;

@RestController
@RequestMapping("/api/academic-years")
public class AcademicYearController {

    private final AcademicYearService academicYearService;

    @Autowired
    public AcademicYearController(AcademicYearService academicYearService) {
        this.academicYearService = academicYearService;
    }

    /**
     * ✅ Create academic year via POST /api/academic-years/create
     */
    @PostMapping("/create")
    public ResponseEntity<AcademicYear> createAcademicYear(@Valid @RequestBody AcademicYearRequest request) {
        AcademicYear createdYear = academicYearService.addAcademicYear(request);
        return ResponseEntity.ok(createdYear);
    }

    /**
     * 📋 Get all academic years for a mobility
     */
    @GetMapping("/mobility/{mobilityId}")
    public ResponseEntity<List<AcademicYear>> getAcademicYearsByMobility(@PathVariable Long mobilityId) {
        return ResponseEntity.ok(academicYearService.getYearsByMobility(mobilityId));
    }

    /**
     * 🔍 Search academic years by label
     */
    @GetMapping("/search")
    public ResponseEntity<List<AcademicYear>> searchByLabel(@RequestParam String keyword) {
        return ResponseEntity.ok(academicYearService.searchByLabel(keyword));
    }

    /**
     * 🔍 Get academic year by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AcademicYear> getById(@PathVariable Long id) {
        return ResponseEntity.ok(academicYearService.getById(id));
    }

    /**
     * 📊 Get average converted grade for an academic year
     */
    @GetMapping("/{id}/average")
    public ResponseEntity<Double> getAverageGradeForAcademicYear(@PathVariable Long id) {
        double average = academicYearService.getAverageConvertedGradeForYear(id);
        return ResponseEntity.ok(average);
    }

    /**
     * ❌ Delete academic year by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteYear(@PathVariable Long id) {
        academicYearService.deleteYear(id);
        return ResponseEntity.noContent().build();
    }
}
