package mancala.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MancalaTest {
    private Mancala mancala;
    private static String name1;
    private static String name2;

    @BeforeEach
    public void init() {
        name1 = "Alice";
        name2 = "Bob";
        mancala = new Mancala(name1, name2);
    }

    @Test
    public void testNamePlayer1returnName1() {
        assertEquals(name1, mancala.getNameOfPlayerOne());
    }

    @Test
    public void testNamePlayer2returnName2() {
        assertEquals(name2, mancala.getNameOfPlayerTwo());
    }

    @Test
    public void startOfGameShouldBeTurnPlayer1() {
        assertTrue(mancala.isPlayersTurn(name1));
    }

    @Test
    public void startOfGameShouldBeTurnPlayer2() {
        assertFalse(mancala.isPlayersTurn(name2));
    }

    @Test
    public void isPlayerTurnOfRandomNameReturnFalse() {
        assertFalse(mancala.isPlayersTurn("adfa"));
    }

    @Test 
    public void isPlayer1TurnShouldBeFalseAfterFirstPlayerMadeMove() {
        mancala.playPit(0);
        assertFalse(mancala.isPlayersTurn(name1));
    }

    @Test
    public void isPlayer2TurnShouldBeTrueAfterFirstPlayerMadeMove() {
        mancala.playPit(0);
        assertTrue(mancala.isPlayersTurn(name2));
    }

    @ParameterizedTest
    @ValueSource(ints = {6,13})
    public void numStonesKalahaAtStartIs0(int index) {
        assertEquals(0, mancala.getStonesForPit(index));
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4,5,7,8,9,10,11,12})
    public void numStonesPitAtStartIs4(int index) {
        assertEquals(4, mancala.getStonesForPit(index));
    }

    @Test
    public void possibleToPlayFirstPit() {
        int index = 0;
        mancala.playPit(index);
        assertEquals(0, mancala.getStonesForPit(index));
    }

//    @Test
//    public void possibleToPlayOpponentFirstPit() {
//        int index = 7;
//        mancala.playPit(index);
//        assertEquals(0, mancala.getStonesForPit(index));
//    }

    @Test
    public void isEndOfGameIsFalseAtStart() {
        assertFalse(mancala.isEndOfGame());
    }

    @AfterEach
    public void teardown() {
        mancala = null;
    }
}