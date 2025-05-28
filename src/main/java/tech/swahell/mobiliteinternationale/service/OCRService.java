package tech.swahell.mobiliteinternationale.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class OCRService {

    /**
     * Extracts text from a PDF file using Tesseract OCR.
     *
     * @param pdfPath the absolute path to the uploaded PDF file
     * @return the extracted text as a String
     */
    public String extractTextFromPdf(String pdfPath) {
        StringBuilder extractedText = new StringBuilder();

        try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            Tesseract tesseract = new Tesseract();

            // Optional: Set Tesseract data path if not in system PATH
            // tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
            tesseract.setLanguage("eng");

            for (int page = 0; page < document.getNumberOfPages(); page++) {
                BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300);
                File tempImage = File.createTempFile("page_" + page, ".png");

                try {
                    ImageIO.write(image, "png", tempImage);
                    String pageText = tesseract.doOCR(tempImage);
                    extractedText.append(pageText).append("\n");
                } finally {
                    if (tempImage.exists()) {
                        tempImage.delete(); // Clean up temp file
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read or render the PDF: " + e.getMessage(), e);
        } catch (TesseractException e) {
            throw new RuntimeException("Tesseract OCR failed: " + e.getMessage(), e);
        }

        return extractedText.toString();
    }
}
