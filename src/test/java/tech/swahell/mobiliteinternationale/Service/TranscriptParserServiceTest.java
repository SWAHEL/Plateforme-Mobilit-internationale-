package tech.swahell.mobiliteinternationale.Service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.swahell.mobiliteinternationale.service.TranscriptParserService;

@SpringBootTest
public class TranscriptParserServiceTest {

    @Autowired
    private TranscriptParserService transcriptParserService;

    @Test
    public void testParseTranscriptTextAndSave() {
        String transcriptText = """
            name: Yassine Elhanouni
            programme: Machine Learning
            type: EXCHANGE
            date: 2024-2025
            S3
            Math: 15.5
            Physics: 14.0
            Chemistry: 16.5
            S4
            AI: 18.0
            Databases: 17.0
            Operating Systems: 16.0
            """;

        transcriptParserService.parseTranscriptTextAndSave(transcriptText);
        System.out.println("✅ Transcript parsing completed.");
    }
}
