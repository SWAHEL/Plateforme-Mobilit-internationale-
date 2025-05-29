package tech.swahell.mobiliteinternationale.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import tech.swahell.mobiliteinternationale.entity.Decision;
import tech.swahell.mobiliteinternationale.entity.Mobility;
import tech.swahell.mobiliteinternationale.repository.MobilityRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class PVGeneratorService {

    private final MobilityRepository mobilityRepository;

    public PVGeneratorService(MobilityRepository mobilityRepository) {
        this.mobilityRepository = mobilityRepository;
    }

    /**
     * Génère un PV PDF pour une décision et retourne le chemin complet du fichier généré.
     */
    public String generatePVForDecision(Decision decision) {
        Mobility mobility = decision.getMobility();
        if (mobility == null) {
            throw new RuntimeException("Aucune mobilité associée à la décision.");
        }

        try {
            // 📁 Dossier de destination
            String outputDir = "generated/pvs";
            File directory = new File(outputDir);
            if (!directory.exists()) {
                Files.createDirectories(directory.toPath());
            }

            // 📄 Nom unique pour le fichier PDF
            String fileName = "PV_" + mobility.getId() + "_" + UUID.randomUUID() + ".pdf";
            String fullPath = outputDir + "/" + fileName;

            // 📄 Création du document PDF
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream(fullPath));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            // Titre
            Paragraph title = new Paragraph("Procès-Verbal de Délibération", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Membres présents
            document.add(new Paragraph("En présence de :", sectionFont));
            List<String> membresCommission = List.of(
                    "M. El Idrissi Mohamed", "M. Benali Youssef", "M. Ouahbi Karim",
                    "Mme. Zahra El Fassi", "M. Amine Bouziane"
            );
            for (String membre : membresCommission) {
                document.add(new Paragraph("• " + membre, textFont));
            }
            document.add(Chunk.NEWLINE);

            // Étudiant
            document.add(new Paragraph("Informations de l’étudiant :", sectionFont));
            document.add(new Paragraph("Nom complet : " + mobility.getStudent().getFullName(), textFont));
            document.add(new Paragraph("Filière : " + mobility.getStudent().getFiliere().name(), textFont));
            document.add(new Paragraph("Université partenaire : " + mobility.getStudent().getPartner().getUniversityName(), textFont));
            document.add(new Paragraph("Programme : " + mobility.getProgram(), textFont));
            document.add(new Paragraph("Période de mobilité : " + mobility.getStartDate() + " au " + mobility.getEndDate(), textFont));
            document.add(Chunk.NEWLINE);

            // Décision
            document.add(new Paragraph("Décision de la commission :", sectionFont));
            document.add(new Paragraph("Après analyse approfondie du dossier académique et des éléments justificatifs transmis par l'étudiant, la commission pédagogique rend la décision suivante :", textFont));
            document.add(new Paragraph("➤ Verdict final : " + decision.getVerdict().name(), textFont));
            document.add(new Paragraph("➤ Mention attribuée : " + decision.getMention(), textFont));
            document.add(new Paragraph("➤ Commentaire : " + (decision.getComment() != null ? decision.getComment() : "Aucun commentaire"), textFont));
            document.add(new Paragraph("➤ Décision rendue par : " + decision.getMadeBy() + " (" + decision.getMadeByRole().name() + ")", textFont));
            document.add(new Paragraph("➤ Date de délibération : " + decision.getDecisionDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), textFont));
            document.add(Chunk.NEWLINE);

            // Conclusion
            document.add(new Paragraph("Fait à Rabat, le " + decision.getDecisionDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")), textFont));
            document.close();

            return fullPath;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PV : " + e.getMessage(), e);
        }
    }
}
