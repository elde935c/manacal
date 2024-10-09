package mancala.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PitTest {
    static Pit pit;
    static int numPlayers = 2;
    static int numBowlsPerPlayer = 7;
    static int numBowls = numPlayers*numBowlsPerPlayer;
    static List<Integer> startingStones;
    static int pitStartingStones = 4;
    static ArrayList<String> names;

    @BeforeAll
    public static void setup() {

    }

    @BeforeEach
    public void init() {
        startingStones = new ArrayList<>();
        for (int i=1; i<=numBowls; i++) {
            startingStones.add((i%numBowlsPerPlayer==0) ? 0 : pitStartingStones);
        }
        names = new ArrayList<>();
        names.add("Mario");
        names.add("Luigi");
        pit = new Pit(numBowls, numBowlsPerPlayer, startingStones, names);

    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6})
    void playerOfOppositeShouldBeOpponent(int i) {
        Player playerOne = pit.getBowlAt(i).getPlayer();
        Player playerTwo = pit.getBowlAt(i+7).getPlayer();

        assertEquals(playerOne.getOpponent(), playerTwo);
    }

    @Test
    void numStonesShouldBeZeroAfterPlay() {
        pit.playPit(1);
        assertEquals(0, pit.getStones());
    }

    @Test
    void nextPitShouldHaveFiveStonesAfterPlay() {
        pit.playPit(1);
        assertEquals(5, pit.nextBowl.getStones());
    }

    @Test
    void onePitTooFarShouldNotGetStones() {
        pit.playPit(1);
        assertEquals(4, pit.getBowlAt(6).getStones());
    }

    @ParameterizedTest
    @CsvSource({"1,0", "2,5", "5,5", "6,4"})
    void testNumStonesAfterPlayOfMultiplePits(int pitNumber, int numStones) {
        pit.playPit(1);
        assertEquals(numStones, pit.getBowlAt(pitNumber).getStones());
    }

    @Test
    void oppositeOfOppositeBowlShouldBeThisPit() {
        assertEquals(pit, pit.getOppositeBowl().getOppositeBowl());
    }

    @Test
    void oppositeOfFirstPitShouldBeAfterOpponentsKalaha() {
        assertEquals(pit, pit.getOppositeBowl().getKalaha().getNextBowl());
    }

    @Test
    void oppositeBowlShouldBeEmptyAfterSteal() {
        pit.empty();
        pit.nextBowl.passStones(numBowls-1, pit.getPlayer());
        assertEquals(0, pit.getOppositeBowl().getStones());
    }

    @Test
    void pitShouldBeEmptyAfterSteal() {
        pit.empty();
        pit.nextBowl.passStones(numBowls-1, pit.getPlayer());
        assertEquals(0, pit.getStones());
    }

    @Test
    void kalahaShouldGainStonesAfterSteal() {
        pit.empty();
        pit.nextBowl.passStones(numBowls-1, pit.getPlayer());
        assertEquals(1+5+1, pit.getKalaha().getStones());
    }

    @Test
    void kalahaShouldGetNothingWhenEndingInEmptyPitOpponent() {
        pit.getOppositeBowl().empty();
        pit.nextBowl.passStones(numBowls-2, pit.getPlayer());
        assertEquals(1, pit.getKalaha().getStones());
    }

    @Test
    void emptyPitOpponentGetsOneWhenEndingInEmptyPitOpponent() {
        pit.getOppositeBowl().empty();
        pit.nextBowl.passStones(numBowls-2, pit.getPlayer());
        assertEquals(1, pit.getOppositeBowl().getStones());
    }

    @Test
    void turnOpponentShouldBeTrueAfterEndingInPit() {
        pit.playPit(1);
        assertTrue(pit.getPlayer().getOpponent().getTurn());
    }

    @Test
    void turnThisPlayerShouldBeFalseAfterEndingInPit() {
        pit.playPit(1);
        assertFalse(pit.getPlayer().getTurn());
    }

    @Test
    void turnOpponentShouldBeFalseAfterEndingInKalaha() {
        pit.playPit(numBowlsPerPlayer-4);
        assertFalse(pit.getPlayer().getOpponent().getTurn());
    }

    @Test
    void turnThisPlayerShouldBeTrueAfterEndingInKalaha() {
        pit.nextBowl.passStones(numBowlsPerPlayer-1, pit.getPlayer());
        assertTrue(pit.getPlayer().getTurn());
    }

    @Test
    void testScoreAfterGameFinishedReturn24() {
        pit.finishGame(0);
        assertEquals(pitStartingStones*(numBowlsPerPlayer-1), pit.getScore());
    }

    @Test
    void pitsShouldBeEmptyAfterFinishingGame() {
        pit.finishGame(0);
        assertFalse(pit.hasStonesLeft());
    }

    @Test
    void notEmptyAtStartOfGame() {
        assertTrue(pit.hasStonesLeft());
    }

    @Test
    public void noPointsAtStartOfGame() {
        assertEquals(0, pit.getScore());
    }
//
    @Test
    public void onePointAfterFirstMove() {
        pit.playPit(numBowlsPerPlayer-1);
        assertEquals(1, pit.getScore());
    }


    @Test
    public void thereShouldBeNoStonesLeftForOpponentAfterFinishGame() {
        pit.finishGame(0);
        assertFalse(pit.getBowlAt(8).hasStonesLeft());
    }

    @Test
    public void drawAfterFinishGameAtStart() {
        pit.finishGame(0);
        assertEquals(Player.STATE.DRAW, pit.getPlayer().getState());
    }

    @AfterEach
    public void teardown() {
        pit = null;
        names = null;
    }

}