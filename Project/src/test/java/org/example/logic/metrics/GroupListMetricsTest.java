package org.example.logic.metrics;

import org.example.data.Coordinate;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class GroupListMetricsTest {

    static MockedStatic<PairMetrics> mockedPairMetrics;
    static MockedStatic<GroupMetrics> mockedGroupMetrics;
    static List<GroupMatched> mockGroups;
    static List<GroupMatched> emptyGroupList;

    @BeforeAll
    static void setup() {
        mockedPairMetrics = Mockito.mockStatic(PairMetrics.class);
        mockedGroupMetrics = Mockito.mockStatic(GroupMetrics.class);

        emptyGroupList = new ArrayList<>();
        mockGroups = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            GroupMatched mockGroup = Mockito.mock(GroupMatched.class);
            List<PairMatched> mockPairs = new ArrayList<>();

            for (int j = 0; j < 3; j++) {
                mockPairs.add(Mockito.mock(PairMatched.class));
            }

            Mockito.when(mockGroup.getPairList()).thenReturn(mockPairs);
            mockGroups.add(mockGroup);
        }
    }

    @AfterAll
    static void close() {
        mockedPairMetrics.close();
        mockedGroupMetrics.close();
    }

    @Test
    void calcAgeDifference() {
        Mockito.when(PairMetrics.calcAgeDifference(mockGroups.get(0).getPairList().get(0))).thenReturn(0.0);
        Mockito.when(PairMetrics.calcAgeDifference(mockGroups.get(0).getPairList().get(1))).thenReturn(3.0);
        Mockito.when(PairMetrics.calcAgeDifference(mockGroups.get(0).getPairList().get(2))).thenReturn(2.0);
        Mockito.when(PairMetrics.calcAgeDifference(mockGroups.get(1).getPairList().get(0))).thenReturn(0.0);
        Mockito.when(PairMetrics.calcAgeDifference(mockGroups.get(1).getPairList().get(1))).thenReturn(4.0);
        Mockito.when(PairMetrics.calcAgeDifference(mockGroups.get(1).getPairList().get(2))).thenReturn(0.0);

        Assertions.assertEquals(1.5, GroupListMetrics.calcAgeDifference(mockGroups));
    }

    @Test
    void calcAgeDifference_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> GroupListMetrics.calcAgeDifference(emptyGroupList));
    }

    @Test
    void calcGenderDiversity() {
        Mockito.when(GroupMetrics.calcGenderDiversity(mockGroups.get(0))).thenReturn(0.5);
        Mockito.when(GroupMetrics.calcGenderDiversity(mockGroups.get(1))).thenReturn(0.0);

        Assertions.assertEquals(0.25, GroupListMetrics.calcGenderDiversity(mockGroups));
    }

    @Test
    void calcGenderDiversity_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> GroupListMetrics.calcGenderDiversity(emptyGroupList));
    }

    @Test
    void calcPreferenceDeviation() {
        Mockito.when(PairMetrics.calcPreferenceDeviation(mockGroups.get(0).getPairList().get(0))).thenReturn(0.0);
        Mockito.when(PairMetrics.calcPreferenceDeviation(mockGroups.get(0).getPairList().get(1))).thenReturn(0.0);
        Mockito.when(PairMetrics.calcPreferenceDeviation(mockGroups.get(0).getPairList().get(2))).thenReturn(1.0);
        Mockito.when(PairMetrics.calcPreferenceDeviation(mockGroups.get(1).getPairList().get(0))).thenReturn(0.0);
        Mockito.when(PairMetrics.calcPreferenceDeviation(mockGroups.get(1).getPairList().get(1))).thenReturn(2.0);
        Mockito.when(PairMetrics.calcPreferenceDeviation(mockGroups.get(1).getPairList().get(2))).thenReturn(0.0);

        Assertions.assertEquals(0.5, GroupListMetrics.calcPreferenceDeviation(mockGroups));
    }

    @Test
    void calcPreferenceDeviation_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> GroupListMetrics.calcPreferenceDeviation(emptyGroupList));
    }

    @Test
    void calcPathLength() {
        Coordinate location = Mockito.mock(Coordinate.class);
        Mockito.when(PairMetrics.calcPathLength(mockGroups.get(0).getPairList().get(0), location)).thenReturn(3.0);
        Mockito.when(PairMetrics.calcPathLength(mockGroups.get(0).getPairList().get(1), location)).thenReturn(1.0);
        Mockito.when(PairMetrics.calcPathLength(mockGroups.get(0).getPairList().get(2), location)).thenReturn(4.0);
        Mockito.when(PairMetrics.calcPathLength(mockGroups.get(1).getPairList().get(0), location)).thenReturn(1.0);
        Mockito.when(PairMetrics.calcPathLength(mockGroups.get(1).getPairList().get(1), location)).thenReturn(5.0);
        Mockito.when(PairMetrics.calcPathLength(mockGroups.get(1).getPairList().get(2), location)).thenReturn(0.5);

        Assertions.assertEquals(14.5, GroupListMetrics.calcPathLength(mockGroups, location));
    }

    @Test
    void calcPathLength_emptyList() {
        Coordinate location = Mockito.mock(Coordinate.class);
        Assertions.assertEquals(0, GroupListMetrics.calcPathLength(emptyGroupList, location));
    }

    @Test
    void isValid_true() {
        Mockito.when(GroupMetrics.isValid(mockGroups.get(0))).thenReturn(true);
        Mockito.when(GroupMetrics.isValid(mockGroups.get(1))).thenReturn(true);

        Assertions.assertTrue(GroupListMetrics.isValid(mockGroups));
    }

    @Test
    void isValid_false() {
        Mockito.when(GroupMetrics.isValid(mockGroups.get(0))).thenReturn(true);
        Mockito.when(GroupMetrics.isValid(mockGroups.get(1))).thenReturn(false);

        Assertions.assertFalse(GroupListMetrics.isValid(mockGroups));
    }

    @Test
    void listHasGroupsWithSamePairs_true_AllPairsAreTheSame() {
        GroupMatched mockGroupA = Mockito.mock(GroupMatched.class);
        GroupMatched mockGroupB = Mockito.mock(GroupMatched.class);

        PairMatched mockPairA = Mockito.mock(PairMatched.class);
        PairMatched mockPairB = Mockito.mock(PairMatched.class);
        PairMatched mockPairC = Mockito.mock(PairMatched.class);

        List<PairMatched> pairsA = List.of(mockPairA, mockPairB, mockPairC);
        List<PairMatched> pairsB = List.of(mockPairA, mockPairB, mockPairC);
        List<GroupMatched> groups = List.of(mockGroupA, mockGroupB);

        Mockito.when(mockGroupA.getPairList()).thenReturn(pairsA);
        Mockito.when(mockGroupB.getPairList()).thenReturn(pairsB);

        Assertions.assertTrue(GroupListMetrics.listHasGroupsWithIdenticalPairs(mockGroupA, groups));
    }

    @Test
    void listHasGroupsWithSamePairs_true_TwoPairsAreTheSame() {
        GroupMatched mockGroupA = Mockito.mock(GroupMatched.class);
        GroupMatched mockGroupB = Mockito.mock(GroupMatched.class);

        PairMatched mockPairA = Mockito.mock(PairMatched.class);
        PairMatched mockPairB = Mockito.mock(PairMatched.class);
        PairMatched mockPairC = Mockito.mock(PairMatched.class);
        PairMatched mockPairD = Mockito.mock(PairMatched.class);

        List<PairMatched> pairsA = List.of(mockPairA, mockPairB, mockPairC);
        List<PairMatched> pairsB = List.of(mockPairA, mockPairB, mockPairD);
        List<GroupMatched> groups = List.of(mockGroupA, mockGroupB);

        Mockito.when(mockGroupA.getPairList()).thenReturn(pairsA);
        Mockito.when(mockGroupB.getPairList()).thenReturn(pairsB);

        Assertions.assertTrue(GroupListMetrics.listHasGroupsWithIdenticalPairs(mockGroupA, groups));
    }

    @Test
    void listHasGroupsWithSamePairs_false_OnePairIsTheSame() {
        GroupMatched mockGroupA = Mockito.mock(GroupMatched.class);
        GroupMatched mockGroupB = Mockito.mock(GroupMatched.class);

        PairMatched mockPairA = Mockito.mock(PairMatched.class);
        PairMatched mockPairB = Mockito.mock(PairMatched.class);
        PairMatched mockPairC = Mockito.mock(PairMatched.class);
        PairMatched mockPairD = Mockito.mock(PairMatched.class);
        PairMatched mockPairE = Mockito.mock(PairMatched.class);

        List<PairMatched> pairsA = List.of(mockPairA, mockPairB, mockPairC);
        List<PairMatched> pairsB = List.of(mockPairA, mockPairD, mockPairE);
        List<GroupMatched> groups = List.of(mockGroupA, mockGroupB);

        Mockito.when(mockGroupA.getPairList()).thenReturn(pairsA);
        Mockito.when(mockGroupB.getPairList()).thenReturn(pairsB);

        Assertions.assertFalse(GroupListMetrics.listHasGroupsWithIdenticalPairs(mockGroupA, groups));
    }

    @Test
    void listHasGroupsWithSamePairs_false_NoPairIsTheSame() {
        GroupMatched mockGroupA = Mockito.mock(GroupMatched.class);
        GroupMatched mockGroupB = Mockito.mock(GroupMatched.class);

        PairMatched mockPairA = Mockito.mock(PairMatched.class);
        PairMatched mockPairB = Mockito.mock(PairMatched.class);
        PairMatched mockPairC = Mockito.mock(PairMatched.class);
        PairMatched mockPairD = Mockito.mock(PairMatched.class);
        PairMatched mockPairE = Mockito.mock(PairMatched.class);
        PairMatched mockPairF = Mockito.mock(PairMatched.class);

        List<PairMatched> pairsA = List.of(mockPairA, mockPairB, mockPairC);
        List<PairMatched> pairsB = List.of(mockPairD, mockPairE, mockPairF);
        List<GroupMatched> groups = List.of(mockGroupA, mockGroupB);

        Mockito.when(mockGroupA.getPairList()).thenReturn(pairsA);
        Mockito.when(mockGroupB.getPairList()).thenReturn(pairsB);

        Assertions.assertFalse(GroupListMetrics.listHasGroupsWithIdenticalPairs(mockGroupA, groups));
    }

    @Test
    void getPairsInGroups() {
        GroupMatched mockGroupA = Mockito.mock(GroupMatched.class);
        GroupMatched mockGroupB = Mockito.mock(GroupMatched.class);

        PairMatched mockPairA = Mockito.mock(PairMatched.class);
        PairMatched mockPairB = Mockito.mock(PairMatched.class);
        PairMatched mockPairC = Mockito.mock(PairMatched.class);

        List<PairMatched> pairsA = List.of(mockPairA, mockPairC);
        List<PairMatched> pairsB = List.of(mockPairB, mockPairC);
        List<GroupMatched> groups = List.of(mockGroupA, mockGroupB);

        Mockito.when(mockGroupA.getPairList()).thenReturn(pairsA);
        Mockito.when(mockGroupB.getPairList()).thenReturn(pairsB);

        List<PairMatched> expected = List.of(mockPairA, mockPairB, mockPairC);
        List<PairMatched> actual = GroupListMetrics.getPairsInGroups(groups);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }
}