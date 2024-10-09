package mancala.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KalahaTest {
    static Pit pit;
    static int numPlayers = 2;
    static int numBowlsPerPlayer = 3;
    static int numBowls = numPlayers*numBowlsPerPlayer;
    static List<Integer> startingStones;
    static int pitStartingStones = 4;
    static ArrayList<String> names;

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


    @Test
    public void getNumberOfStonesInPit() {
        assertEquals(4, pit.getStones());
    }

    @Test
    public void getNumberOfStonesInKalaha() {
        Kalaha kalaha = pit.getKalaha();
        assertEquals(0, kalaha.getStones());
    }

    @Test
    public void testIfFirstKalahaIsOnCorrectPlaceShouldThrowClassCastException() {
        Kalaha kalaha = pit.getBowlAt(numBowlsPerPlayer);
        assertThrows(ClassCastException.class,
                () -> ((Pit) kalaha).playPit(1));
    }

    @Test
    public void testIfSecondKalahaIsOnCorrectPlaceShouldThrowClassCastException() {
        Kalaha kalaha = pit.getBowlAt(numBowls);
        assertThrows(ClassCastException.class,
                () -> ((Pit) kalaha).playPit(1));
    }

    @Test
    public void testIfLoopIsClosed() {
        Kalaha kalaha = pit.getBowlAt(numBowls);
        assertNotEquals(null, kalaha.getNextBowl());
    }

    @Test
    public void testForGetPlayerOfRootBowl() {
        assertNotEquals(null, pit.getPlayer());
    }

    @Test
    public void testForGetPlayerOfOtherBowl() {
        assertNotEquals(null, pit.getNextBowl().getPlayer());
    }

    @Test
    public void testForGetPlayerOfKalaha() {
        assertNotEquals(null, pit.getKalaha().getPlayer());
    }

    @Test
    public void testIfPlayersTurnReturnTrueOrFalse() {
        assertNotEquals(null, pit.getPlayer().getTurn());
    }


    @Test
    void stonesInOwnKalahaShouldGainOne() {
        pit.passStones(numBowls+1, pit.getPlayer());
        Kalaha kalaha = pit.getKalaha();
        assertEquals(1, kalaha.getStones());
    }

    @Test
    void stonesInPitAfterOwnKalahaShouldGetOneMoreAfterPassStones() {
        pit.passStones(numBowls+1, pit.getPlayer());
        Kalaha opponentsPit = pit.getKalaha().getNextBowl();
        assertEquals(5, opponentsPit.getStones());
    }

    @Test
    void stonesInKalahaOpponentShouldRemainZeroAfterPassStones() {
        pit.passStones(numBowls*3, pit.getPlayer());
        Kalaha opponentsKalaha = pit.getOppositeBowl().getKalaha();
        assertEquals(0, opponentsKalaha.getStones());
    }

    @AfterEach
    public void teardown() {
        pit = null;
        names = null;
    }
}