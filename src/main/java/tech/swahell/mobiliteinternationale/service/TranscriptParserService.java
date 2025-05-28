package tech.swahell.mobiliteinternationale.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.swahell.mobiliteinternationale.dto.SemesterRequest;
import tech.swahell.mobiliteinternationale.entity.*;
import tech.swahell.mobiliteinternationale.entity.Module;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TranscriptParserService {

    private final StudentService studentService;
    private final MobilityService mobilityService;
    private final AcademicYearService academicYearService;
    private final SemesterService semesterService;
    private final ModuleService moduleService;

    @Autowired
    public TranscriptParserService(StudentService studentService,
                                   MobilityService mobilityService,
                                   AcademicYearService academicYearService,
                                   SemesterService semesterService,
                                   ModuleService moduleService) {
        this.studentService = studentService;
        this.mobilityService = mobilityService;
        this.academicYearService = academicYearService;
        this.semesterService = semesterService;
        this.moduleService = moduleService;
    }

    @Transactional
    public void parseTranscriptTextAndSave(String text) {
        String studentName = extractValue(text, "name:\\s*(.+)");
        String program = extractValue(text, "programme\\s*:\\s*(.+)");
        String typeString = extractValue(text, "type\\s*:\\s*(\\w+)");
        String yearLabel = extractValue(text, "date\\s*:\\s*(\\d{4}-\\d{4})");

        if (studentName == null || program == null || typeString == null || yearLabel == null) {
            throw new RuntimeException("Transcript is missing required information.");
        }

        MobilityType mobilityType = MobilityType.valueOf(typeString.toUpperCase().trim());

        Student student = studentService.findByFullName(studentName.trim())
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable : " + studentName));

        // Set default start and end dates from academic year
        String[] years = yearLabel.split("-");
        LocalDate startDate = LocalDate.parse(years[0] + "-09-01", DateTimeFormatter.ISO_DATE);
        LocalDate endDate = LocalDate.parse(years[1] + "-06-30", DateTimeFormatter.ISO_DATE);

        Mobility savedMobility = mobilityService.createMobility(
                student.getId(),
                mobilityType,
                program.trim(),
                startDate,
                endDate
        );

        AcademicYear year = new AcademicYear();
        year.setYearLabel(yearLabel.trim());
        year.setMobility(savedMobility);
        AcademicYear savedYear = academicYearService.getOrCreateYear(year);

        // Extract semesters dynamically (e.g., S3, S4, Semester 1...)
        List<String> semesterLabels = extractSemesterLabels(text);
        for (int i = 0; i < semesterLabels.size(); i++) {
            String current = semesterLabels.get(i);
            String next = (i + 1 < semesterLabels.size()) ? semesterLabels.get(i + 1) : null;

            SemesterRequest request = new SemesterRequest(current, SemesterType.NORMAL, savedYear.getId());
            Semester semester = semesterService.addSemester(savedYear.getId(), request);

            List<Module> modules = extractModulesFromSection(text, current, next, semester);
            modules.forEach(moduleService::save);
        }
    }

    private List<String> extractSemesterLabels(String text) {
        List<String> labels = new ArrayList<>();
        Pattern pattern = Pattern.compile("(?i)(S\\d+|Semester \\d+)");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            labels.add(matcher.group().trim());
        }
        return labels;
    }

    private List<Module> extractModulesFromSection(String text, String startMarker, String endMarker, Semester semester) {
        List<Module> modules = new ArrayList<>();
        String section = extractSection(text, startMarker, endMarker);
        Pattern modulePattern = Pattern.compile("(.+?):\\s*(\\d+(\\.\\d+)?)");

        Matcher matcher = modulePattern.matcher(section);
        while (matcher.find()) {
            String name = matcher.group(1).trim();
            double grade = Double.parseDouble(matcher.group(2));

            Module module = new Module();
            module.setName(name);
            module.setOriginalGrade(grade);
            module.setConvertedGrade(convertGrade(grade));
            module.setEcts(5);
            module.setPfe(name.toLowerCase().contains("pfe"));
            module.setSemester(semester);
            modules.add(module);
        }
        return modules;
    }

    private double convertGrade(double grade) {
        return Math.min(grade * 1.2, 20.0);
    }

    private String extractValue(String text, String regex) {
        Matcher matcher = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(text);
        return matcher.find() ? matcher.group(1).trim() : null;
    }

    private String extractSection(String text, String startMarker, String endMarker) {
        int start = text.indexOf(startMarker);
        int end = (endMarker != null) ? text.indexOf(endMarker, start + startMarker.length()) : text.length();
        return (start != -1 && end != -1) ? text.substring(start, end).trim() : "";
    }
}
