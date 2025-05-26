package tech.swahell.mobiliteinternationale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.swahell.mobiliteinternationale.dto.StudentRequest;
import tech.swahell.mobiliteinternationale.entity.Filiere;
import tech.swahell.mobiliteinternationale.entity.Partner;
import tech.swahell.mobiliteinternationale.entity.Student;
import tech.swahell.mobiliteinternationale.service.PartnerService;
import tech.swahell.mobiliteinternationale.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final PartnerService partnerService;

    @Autowired
    public StudentController(StudentService studentService, PartnerService partnerService) {
        this.studentService = studentService;
        this.partnerService = partnerService;
    }

    /**
     * 👤 Admin creates a new student
     */
    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody StudentRequest request) {
        Partner partner = partnerService.getPartnerById(request.getPartnerId());

        Student student = studentService.addStudent(
                request.getFullName(),
                request.getEmail(),
                request.getPassword(),
                Filiere.valueOf(request.getFiliere()),
                partner,
                request.getCurrentUserRole()
        );

        return ResponseEntity.ok(student);
    }

    /**
     * 📋 Get all students
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    /**
     * 🔍 Get student by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    /**
     * 🔍 Search students by full name
     */
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchByKeyword(@RequestParam String keyword) {
        return ResponseEntity.ok(studentService.searchByKeyword(keyword));
    }

    /**
     * ❌ Delete a student (admin-only)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id, @RequestParam String currentUserRole) {
        studentService.deleteStudent(id, currentUserRole);
        return ResponseEntity.noContent().build();
    }
}
