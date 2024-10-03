package mancala.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    static Game game;
    int numBowlsPerPlayer = 7;
    int numPlayers = 2;
    int numBowls = numBowlsPerPlayer*numPlayers;
    List<Integer> startingStones;
    static int pitStartingStones = 4;

    @BeforeEach
    public void init() {
        startingStones = new ArrayList<>();
        for (int i=1; i<=numBowls; i++) {
            startingStones.add((i%numBowlsPerPlayer==0) ? 0 : pitStartingStones);
        }
        game = new Game(numPlayers, numBowlsPerPlayer, startingStones);
    }

    @Test
    public void firstPlayerHasTurnAtStart() {
        assertEquals(game.getFirstPit().getPlayer(), game.getPlayerWithTurn());
    }

    @Test
    public void secondPlayerHasTurnAfterMove() {
        game.processMove(1);
        assertEquals(game.getFirstPit().getPlayer().getOpponent(),
                game.getPlayerWithTurn());
    }

    @Test
    public void firstPlayerHasTurnAfterMoveEndsInKalaha() {
        game.processMove(numBowlsPerPlayer-pitStartingStones);
        assertEquals(game.getFirstPit().getPlayer(),
                game.getFirstPit().getPlayerWithTurn());
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6})
    public void NothingShouldHappenAfterIllegalMoveNonExistentPit(int pitNumber) {
        game.processMove(0);
        assertEquals(4, game.getPitAt(pitNumber).getStones());
    }

    @ParameterizedTest
    @CsvSource({"1,0","2,5","3,5","4,5","5,5","6,4"})
    public void NothingShouldHappenAfterIllegalMovePitAlreadyEmpty(int pitNumber,
                                                                   int numStones) {
        game.processMove(1); // first player plays
        game.processMove(1); // opponent plays
        game.processMove(1); // first player plays again
        assertEquals(numStones, game.getPitAt(pitNumber).getStones());
    }

    @Test
    public void checkIfTurnChangesAfterMove()  {
        game.processMove(1);
        assertFalse(game.getFirstPlayer().getTurn());
    }

    @ParameterizedTest
    @CsvSource({"1,0", "2,5", "5,5", "6,4"})
    public void checkStonesAfterFirstTurnIsMade(int pitNumber, int numStones) {
        game.processMove(1);
        assertEquals(numStones,
                game.getPitAt(pitNumber).getStones());
    }

    @Test
    public void gameStillRunningReturnsTrueIfNoSideEmpty() {
        assertTrue(game.getIsGameStillRunning());
    }

    private void setUpBoardWithOnlyOneStoneInLastPit() {
        startingStones = new ArrayList<>();
        for (int i=1; i<=numBowls; i++) {
            startingStones.add((i==numBowlsPerPlayer-1) ? 1 : 0);
        }
    }

    private void setUpBoardWithOnlyOneStoneInLastPitBothSides() {
        startingStones = new ArrayList<>();
        for (int i=1; i<=numBowls; i++) {
            startingStones.add((i%(numBowlsPerPlayer-1)==0) ? 1 : 0);
        }
    }

    private void setUpBoardWithOnlyOneStoneInLastPitButOpponentStillFull() {
        startingStones = new ArrayList<>();
        for (int i=1; i<=numBowlsPerPlayer-2; i++) {
            startingStones.add(0);
        }
        startingStones.add(1);
        startingStones.add(0); //kalaha
        for (int i=1; i<=numBowlsPerPlayer-1; i++) {
            startingStones.add(4);
        }
        startingStones.add(0); //kalaha
    }

    private void setUpGameFinishBySteal() {
        startingStones = new ArrayList<>();
        for (int i=1; i<=numBowls; i++) {
            if (i==numBowlsPerPlayer-2) startingStones.add(1);
            else if (i==numBowlsPerPlayer+1) startingStones.add(1);
            else startingStones.add(0);
        }
    }

    @Test
    public void gameStillRunningReturnsFalseAfterPlayingLastStones() {
        setUpBoardWithOnlyOneStoneInLastPit();
        game = new Game(numPlayers, numBowlsPerPlayer, startingStones);
        game.processMove(6); // first player puts last stone in kalaha
        game.processMove(6); //first player again
        assertFalse(game.getIsGameStillRunning());
    }

    @Test
    public void gameStillRunningReturnsFalseAfterStealingOpponentsLastStones() {
        setUpGameFinishBySteal();
        game = new Game(numPlayers, numBowlsPerPlayer, startingStones);
        game.processMove(5); // steal stones in 1st pit opponent
        game.processMove(1); // random pit by opponent
        assertFalse(game.getIsGameStillRunning());
    }

    @Test
    public void gameStillRunningReturnsFalseAfterPlayingAgainWhenEndingInKalaha() {
        setUpBoardWithOnlyOneStoneInLastPit();
        game = new Game(numPlayers, numBowlsPerPlayer, startingStones);
        game.processMove(6); // first player plays the last stone in the 6th pit
        game.processMove(6); // first player plays again
        assertFalse(game.getIsGameStillRunning());
    }

    @Test
    public void returnGameNotFinishedExceptionWhenAskingForWinnersAtStart() {
        assertNull(game.getWinner());
    }

    @Test
    public void returnFirstPlayerIfFirstPlayerWon() {
        setUpBoardWithOnlyOneStoneInLastPit();
        game = new Game(numPlayers, numBowlsPerPlayer, startingStones);
        game.processMove(6); // first player puts last stone in kalaha
        game.processMove(6); //first player again
        assertEquals(game.getFirstPlayer(), game.getWinner().getFirst());
    }

    @Test
    public void returnSecondPlayerIfSecondPlayerWon() {
        setUpBoardWithOnlyOneStoneInLastPitButOpponentStillFull();
        game = new Game(numPlayers, numBowlsPerPlayer, startingStones);
        game.processMove(6); // first player puts last stone in kalaha
        game.processMove(6); //first player again
        assertEquals(game.getFirstPlayer().getOpponent(),
                game.getWinner().getFirst());
    }

    @Test
    public void returnNullIfItsATie() {
        setUpBoardWithOnlyOneStoneInLastPitBothSides();
        game = new Game(numPlayers, numBowlsPerPlayer, startingStones);
        game.processMove(6); // first player puts last stone in kalaha
        game.processMove(6); //first player again
        assertEquals(game.getFirstPlayer(), game.getWinner().getFirst());
        assertEquals(game.getFirstPlayer().getOpponent(),
                game.getWinner().getLast());
    }

    @AfterEach
    public void teardown() {
        game = null;
    }
}