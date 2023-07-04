package org.example.data.json;
import org.example.data.DataManagement;
import org.example.logic.enums.Criteria;
import org.example.logic.matchingalgorithms.MatchCosts;
import org.example.logic.structures.MatchingRepository;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

/**
 * Class to generate three matchings based on the given importance of criteria
 * 5: Food preference
 * 6: age difference
 * 7. gender difference
 * 8. path length
 * 9. maximum match count
 */
public class GenerateSolutions {

    String filePathParticipants = "src/main/java/org/example/artifacts/teilnehmerliste.csv";
    String filePathPartylocation = "src/main/java/org/example/artifacts/partylocation.csv";
    String projectPath = System.getProperty("user.dir");
    String parentPath = new File(projectPath).getParent();
    String directory = parentPath + "/Loesungen/";

    /**
     * Weighted criteria: 5 > 8 > 6 > 7 > 9
     */
    @Test
    public void generateSolutionA() {
        String fileName = "solutionA.json";

        DataManagement dataManagement = new DataManagement(filePathParticipants, filePathPartylocation);
        MatchingRepository matchingRepository = new MatchingRepository(dataManagement);

        MatchCosts matchCosts = new MatchCosts(
                Criteria.IDENTICAL_FOOD_PREFERENCE,
                Criteria.PATH_LENGTH,
                Criteria.AGE_DIFFERENCE,
                Criteria.GENDER_DIFFERENCE,
                Criteria.MATCH_COUNT);

        matchingRepository.matchPairs(matchCosts);
        matchingRepository.matchGroups(matchCosts);

        try {
            String exportData = Serializer.serializeMatchingRepository(matchingRepository);
            File file = new File(directory, fileName);
            Serializer.writeToFile(exportData, file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Class to generate three matchings based on the given importance of criteria
     * 5: Food preference
     * 6: age difference
     * 7. gender difference
     * 8. path length
     * 9. maximum match count
     */
    /**
     * Weighted criteria: 9 > 5 > 8 > 7 > 6
     */
    @Test
    public void generateSolutionB() {
        String fileName = "solutionB.json";

        DataManagement dataManagement = new DataManagement(filePathParticipants, filePathPartylocation);
        MatchingRepository matchingRepository = new MatchingRepository(dataManagement);

        MatchCosts matchCosts = new MatchCosts(
                Criteria.MATCH_COUNT,
                Criteria.IDENTICAL_FOOD_PREFERENCE,
                Criteria.PATH_LENGTH,
                Criteria.GENDER_DIFFERENCE,
                Criteria.AGE_DIFFERENCE
                );

        matchingRepository.matchPairs(matchCosts);
        matchingRepository.matchGroups(matchCosts);

        try {
            String exportData = Serializer.serializeMatchingRepository(matchingRepository);
            File file = new File(directory, fileName);
            Serializer.writeToFile(exportData, file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Class to generate three matchings based on the given importance of criteria
     * 5: Food preference
     * 6: age difference
     * 7. gender difference
     * 8. path length
     * 9. maximum match count
     */
    /**
     * Weighted criteria: 7 > 6 > 5 > 8 > 9
     */
    @Test
    public void generateSolutionC() {
        String fileName = "solutionC.json";

        DataManagement dataManagement = new DataManagement(filePathParticipants, filePathPartylocation);
        MatchingRepository matchingRepository = new MatchingRepository(dataManagement);

        MatchCosts matchCosts = new MatchCosts(
                Criteria.GENDER_DIFFERENCE,
                Criteria.AGE_DIFFERENCE,
                Criteria.IDENTICAL_FOOD_PREFERENCE,
                Criteria.PATH_LENGTH,
                Criteria.MATCH_COUNT
        );

        matchingRepository.matchPairs(matchCosts);
        matchingRepository.matchGroups(matchCosts);

        try {
            String exportData = Serializer.serializeMatchingRepository(matchingRepository);
            File file = new File(directory, fileName);
            Serializer.writeToFile(exportData, file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
