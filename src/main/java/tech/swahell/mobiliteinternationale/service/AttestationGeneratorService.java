package tech.swahell.mobiliteinternationale.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import tech.swahell.mobiliteinternationale.entity.Decision;
import tech.swahell.mobiliteinternationale.entity.Mobility;
import tech.swahell.mobiliteinternationale.exception.MobilityNotFoundException;
import tech.swahell.mobiliteinternationale.repository.MobilityRepository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class AttestationGeneratorService {

    private final MobilityRepository mobilityRepository;

    public AttestationGeneratorService(MobilityRepository mobilityRepository) {
        this.mobilityRepository = mobilityRepository;
    }

    /**
     * 📄 Génère un fichier PDF d'attestation à partir d'une décision et retourne le chemin.
     */
    public String generateAttestationForDecision(Decision decision) {
        Mobility mobility = decision.getMobility();
        if (mobility == null) {
            throw new IllegalStateException("La décision n'est associée à aucune mobilité.");
        }

        try {
            String fileName = "Attestation_" + mobility.getId() + "_" + UUID.randomUUID() + ".pdf";
            String outputDir = "generated/attestations";
            File directory = new File(outputDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fullPath = outputDir + "/" + fileName;
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream(fullPath));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph title = new Paragraph("Attestation de Réussite", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(30);
            document.add(title);

            String content = String.format(
                    "Nous, soussignés membres de la Commission Pédagogique de l'INPT,\n" +
                            "certifions que l’étudiant(e) :\n\n" +
                            "👤 Nom complet : %s\n" +
                            "🎓 Filière : %s\n" +
                            "🏛️ Université partenaire : %s\n" +
                            "📚 Programme : %s\n" +
                            "📅 Période de mobilité : du %s au %s\n\n" +
                            "a validé avec succès son programme d’échange dans le cadre de la mobilité internationale.\n" +
                            "Mention obtenue : %s.\n\n" +
                            "La présente attestation est délivrée pour servir et valoir ce que de droit.",
                    mobility.getStudent().getFullName(),
                    mobility.getStudent().getFiliere().name(),
                    mobility.getStudent().getPartner().getUniversityName(),
                    mobility.getProgram(),
                    mobility.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    mobility.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    decision.getMention()
            );

            Paragraph contentParagraph = new Paragraph(content, textFont);
            contentParagraph.setAlignment(Element.ALIGN_JUSTIFIED);
            contentParagraph.setSpacingAfter(40);
            document.add(contentParagraph);

            Paragraph signature = new Paragraph("Fait à Rabat, le " +
                    decision.getDecisionDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")), textFont);
            signature.setAlignment(Element.ALIGN_RIGHT);
            document.add(signature);

            Paragraph signLine = new Paragraph("\n\nSignature du Président de la Commission", textFont);
            signLine.setAlignment(Element.ALIGN_RIGHT);
            document.add(signLine);

            document.close();
            return fullPath;

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération de l'attestation : " + e.getMessage(), e);
        }
    }

    /**
     * 📄 Utilisé pour le téléchargement en tant que byte[] via l'endpoint
     */
    public byte[] generateAttestation(Long mobilityId) {
        Mobility mobility = mobilityRepository.findById(mobilityId)
                .orElseThrow(() -> new MobilityNotFoundException("Mobilité non trouvée avec ID : " + mobilityId));

        if (mobility.getDecision() == null) {
            throw new IllegalStateException("Aucune décision enregistrée pour cette mobilité.");
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, baos);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph title = new Paragraph("Attestation de Réussite", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(30);
            document.add(title);

            String content = String.format(
                    "Nous, soussignés membres de la Commission Pédagogique de l'INPT,\n" +
                            "certifions que l’étudiant(e) :\n\n" +
                            "👤 Nom complet : %s\n" +
                            "🎓 Filière : %s\n" +
                            "🏛️ Université partenaire : %s\n" +
                            "📚 Programme : %s\n" +
                            "📅 Période de mobilité : du %s au %s\n\n" +
                            "a validé avec succès son programme d’échange dans le cadre de la mobilité internationale.\n" +
                            "Mention obtenue : %s.\n\n" +
                            "La présente attestation est délivrée pour servir et valoir ce que de droit.",
                    mobility.getStudent().getFullName(),
                    mobility.getStudent().getFiliere().name(),
                    mobility.getStudent().getPartner().getUniversityName(),
                    mobility.getProgram(),
                    mobility.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    mobility.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    mobility.getDecision().getMention()
            );

            Paragraph contentParagraph = new Paragraph(content, textFont);
            contentParagraph.setAlignment(Element.ALIGN_JUSTIFIED);
            contentParagraph.setSpacingAfter(40);
            document.add(contentParagraph);

            Paragraph signature = new Paragraph("Fait à Rabat, le " +
                    mobility.getDecision().getDecisionDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")), textFont);
            signature.setAlignment(Element.ALIGN_RIGHT);
            document.add(signature);

            Paragraph signLine = new Paragraph("\n\nSignature du Président de la Commission", textFont);
            signLine.setAlignment(Element.ALIGN_RIGHT);
            document.add(signLine);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération de l'attestation : " + e.getMessage(), e);
        }
    }
}
