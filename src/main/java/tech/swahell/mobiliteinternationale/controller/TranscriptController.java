package tech.swahell.mobiliteinternationale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.swahell.mobiliteinternationale.service.OCRService;
import tech.swahell.mobiliteinternationale.service.TranscriptParserService;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/transcripts")
public class TranscriptController {

    private final OCRService ocrService;
    private final TranscriptParserService parserService;

    @Autowired
    public TranscriptController(OCRService ocrService, TranscriptParserService parserService) {
        this.ocrService = ocrService;
        this.parserService = parserService;
    }

    /**
     * 📤 Upload a PDF transcript, extract text using OCR, and parse student data
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadTranscript(@RequestParam("file") MultipartFile file) {
        try {
            // 1. Save the uploaded PDF to a temp file
            File tempFile = File.createTempFile("uploaded_", ".pdf");
            file.transferTo(tempFile);

            // 2. Extract text using OCR
            String extractedText = ocrService.extractTextFromPdf(tempFile.getAbsolutePath());

            // 3. Parse and save the data using TranscriptParserService
            parserService.parseTranscriptTextAndSave(extractedText);

            // 4. Cleanup temp file
            tempFile.delete();

            return ResponseEntity.ok("Transcript uploaded, OCR applied, and data parsed successfully.");

        } catch (IOException e) {
            return ResponseEntity.status(500).body("❌ Failed to read the uploaded file: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("❌ Failed to process transcript: " + e.getMessage());
        }
    }
}
