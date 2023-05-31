package org.example.logic.groupmatching;

import org.example.data.DataManagement;
import org.example.logic.repository.MatchingRepository;
import org.example.logic.structures.PairMatched;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryGroupMatchingTest {

    @Test
    void match() {
        String fileToRead = "src/main/java/org/example/artifacts/teilnehmerliste.csv";
        String partyLocation = "src/main/java/org/example/artifacts/partylocation.csv";
        DataManagement dataManagement = new DataManagement(fileToRead, partyLocation);
        MatchingRepository matchingRepository = new MatchingRepository(dataManagement);

        List<PairMatched> pairs = (List<PairMatched>) matchingRepository.getMatchedPairsCollection();
        CategoryGroupMatching.match(pairs);
    }
}