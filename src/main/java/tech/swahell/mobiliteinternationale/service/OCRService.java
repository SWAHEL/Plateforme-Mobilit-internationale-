package tech.swahell.mobiliteinternationale.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.swahell.mobiliteinternationale.exception.OCRServiceException;

import java.util.HashMap;
import java.util.Map;

@Service
public class OCRService {

    private final RestTemplate restTemplate;

    @Value("${ocr.service.url}")
    private String ocrServiceUrl;

    public OCRService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Sends a base64 encoded file to the OCR microservice and retrieves extracted JSON.
     *
     * @param base64EncodedFile Base64 representation of the file
     * @param filename Original filename including extension
     * @return Extracted OCR content in raw JSON string
     */
    public String extractTextFromDocument(String base64EncodedFile, String filename) {
        Map<String, String> payload = new HashMap<>();
        payload.put("fileContent", base64EncodedFile);
        payload.put("filename", filename);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    ocrServiceUrl + "/api/ocr/extract", request, String.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new OCRServiceException("OCR service responded with status: " + response.getStatusCode());
            }

        } catch (Exception e) {
            throw new OCRServiceException("Failed to call OCR service: " + e.getMessage(), e);
        }
    }
}
