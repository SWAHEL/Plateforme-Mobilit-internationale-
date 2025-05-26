package tech.swahell.mobiliteinternationale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.swahell.mobiliteinternationale.entity.Partner;
import tech.swahell.mobiliteinternationale.entity.Student;
import tech.swahell.mobiliteinternationale.entity.Filiere;
import tech.swahell.mobiliteinternationale.exception.StudentNotFoundException;
import tech.swahell.mobiliteinternationale.exception.UnauthorizedAccessException;
import tech.swahell.mobiliteinternationale.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StudentService(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Admin adds a new student with secure password encoding
     */
    public Student addStudent(String fullName, String email, String rawPassword, Filiere filiere, Partner partner, String currentUserRole) {
        if (!"SYSTEM_ADMIN".equals(currentUserRole)) {
            throw new UnauthorizedAccessException("Only admins are allowed to add students.");
        }

        Student student = new Student();
        student.setFullName(fullName);
        student.setEmail(email);
        student.setPassword(passwordEncoder.encode(rawPassword));
        student.setFiliere(filiere);
        student.setPartner(partner);
        return studentRepository.save(student);
    }

    /**
     * Save or update a student (admin only)
     */
    public Student saveStudent(Student student, String currentUserRole) {
        if (!"SYSTEM_ADMIN".equals(currentUserRole)) {
            throw new UnauthorizedAccessException("Only admins are allowed to save or update students.");
        }
        return studentRepository.save(student);
    }

    /**
     * Get student by ID or throw exception
     */
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));
    }

    /**
     * Get student by ID as Optional
     */
    public Optional<Student> findOptionalById(Long id) {
        return studentRepository.findById(id);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> findByFullName(String fullName) {
        return studentRepository.findByFullName(fullName);
    }

    public List<Student> searchByKeyword(String keyword) {
        return studentRepository.findByFullNameContainingIgnoreCase(keyword);
    }

    public List<Student> findByFiliere(Filiere filiere) {
        return studentRepository.findByFiliere(filiere);
    }

    public List<Student> findByPartner(Partner partner) {
        return studentRepository.findByPartner(partner);
    }

    public void deleteStudent(Long id, String currentUserRole) {
        if (!"SYSTEM_ADMIN".equals(currentUserRole)) {
            throw new UnauthorizedAccessException("Only admins can delete a student.");
        }
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Cannot delete: student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }
}
